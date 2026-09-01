# Tasks

One entry per unit of work. `state` is machine-checkable, not self-assessed: it only
becomes `passing` after the listed verification command(s) actually succeed in this
session — never on "this should work" or "the logic is correct."

States: `not_started` / `active` / `blocked` / `passing`.

**WIP = 1**: exactly one task may be `active` at a time. Finish or explicitly `block`
the active task (with a reason) before starting another — no "while I'm here" scope
creep into a second task or an unrelated module. See the WIP rule in `AGENTS.md`.

---

## Harness plan Phase 3 — TASKS.md itself

- **behavior**: introduce this file as the per-task tracking primitive with
  behavior/verification/state fields.
- **verification**: n/a (docs-only change; no Gradle command applies).
- **state**: `passing` (file created and linked from `AGENTS.md`; docs-only changes
  have no build verification, so existence + link is the completion signal).

## Harness plan Phase 4 — Verification tiering explicit in maker/checker routing

- **behavior**: `docs/VERIFICATION.md` now has an explicit "Maker/checker routing"
  section — implementers never self-certify `passing`; a validator's independent
  re-run does, and that re-run must reach layer 3 for any cross-module/UI change.
  `AGENTS.md`'s Task tracking section links to it.
- **verification**: n/a (docs-only change; existence + cross-link is the completion
  signal, same as Phase 3).
- **state**: `passing`.

## Harness plan Phase 5 — Dependabot triage loop

- **behavior**: stand up a `/loop` (or scheduled cloud agent) that merges Dependabot
  PRs on green CI + patch/minor semver, and flags major bumps or red CI for human
  review.
- **verification**: CI status on each Dependabot PR (`.github/workflows/ci.yml` checks)
  — no local Gradle command; the loop's stopping condition is "no open Dependabot PR
  left undecided."
- **state**: `not_started`.

## Harness plan Phase 6 — Monthly harness review cadence

- **behavior**: schedule a recurring re-run of the Fresh Session Test and a prune pass
  over `AGENTS.md`/`docs/` for staleness.
- **verification**: n/a (process, not code).
- **state**: `not_started`.

---

## Merge uiImageList/uiVideoList into uiMediaList

- **behavior**: `:ui:uiMediaList` replaces `:ui:uiImageList` + `:ui:uiVideoList`; one
  `MediaListScreen`/`MediaListViewModel` serves both media types via a `MediaType`
  param, with rendering/behavior parity preserved. Roborazzi screenshot verification is
  explicitly excluded from this task's gate per user direction — the ported test
  files exist but are not run/recorded this session.
- **verification**: `./gradlew ktlintFormat ciLint` (layer 1); `./gradlew jvmTest
  -x :ui:uiMediaList:jvmTest` and `./gradlew testAndroidHostTest
  -x :ui:uiMediaList:testAndroidHostTest` (layer 2, new module's screenshot tests
  excluded per the Roborazzi exclusion above); `./gradlew ciAndroid ciDesktop
  ciWasmJsBrowser`, `./gradlew iosSimulatorArm64Test -x :ui:uiMediaList:iosSimulatorArm64Test`,
  and `./gradlew managedVirtualDeviceDebugAndroidTest managedVirtualDeviceAndroidDeviceTest
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect` (layer 3).
  Requires `JAVA_HOME` pointed at a JDK 17 install — this environment's default JDK 25
  breaks detekt's embedded Kotlin compiler (`IllegalArgumentException: 25.0.3` parsing
  the version string), unrelated to this change.
- **state**: `passing` — independently re-verified by a validator (static review of
  logic/wiring/docs found no defects; a second, execution-capable validator
  independently re-ran the full layer 1-3 gate with `--rerun-tasks` after catching a
  stale/cached false-positive on the managed-device task, and got a genuine fresh
  pass on every command). One transient finding (a `ktlintFormat` convergence diff on
  `MediaListScreen.kt`, six preview functions needing a second formatting pass) was a
  git-staging artifact, not a lint violation — resolved by re-staging; `ktlintCheck`
  passes clean on the current tree.

## Merge uiImageDetails/uiVideoDetails into uiMediaDetails

- **behavior**: `:ui:uiMediaDetails` replaces `:ui:uiImageDetails` + `:ui:uiVideoDetails`;
  one `MediaDetailsScreen`/`MediaDetailsViewModel` serves both media types via a
  `MediaType` param, branching on `hitModel.imageModel != null` to pick a zoomable
  image renderer or a video player. The
  `isTest` screenshot-determinism flag stays confined to the video render path.
  Roborazzi screenshot verification is explicitly excluded from this task's gate per
  user direction — the ported test files exist but are not run/recorded this session.
- **verification**: `./gradlew ktlintFormat ciLint` (layer 1); `./gradlew jvmTest
  -x :ui:uiMediaDetails:jvmTest` and `./gradlew testAndroidHostTest
  -x :ui:uiMediaDetails:testAndroidHostTest` (layer 2); `./gradlew ciAndroid ciDesktop
  ciWasmJsBrowser`, `./gradlew iosSimulatorArm64Test -x :ui:uiMediaDetails:iosSimulatorArm64Test`,
  and `./gradlew managedVirtualDeviceDebugAndroidTest managedVirtualDeviceAndroidDeviceTest
  -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect` (layer 3).
  Requires `JAVA_HOME` pointed at a JDK 17 install (this environment's default JDK 25
  breaks detekt). Note: `ciAndroid`/`ciDesktop`/`ciWasmJsBrowser` shell out to a nested
  `./gradlew` that doesn't inherit an outer `--rerun-tasks` flag — to force a genuinely
  fresh layer-3 re-run, invoke the underlying tasks directly instead (`assembleDebug
  assembleRelease :app:desktopApp:jar :app:webApp:wasmJsMainClasses
  :app:webApp:wasmJsBrowserDistribution --rerun-tasks`).
- **state**: `passing` — independently re-verified by two validators: a static reviewer
  confirmed logic/wiring/`MediaType`-relocation/docs correctness (including that the
  `MediaDetailsVideoSuccessPreview` default-parameter bug — using bare `testData`,
  which has both `imageModel` and `videosModel` non-null, would silently render the
  image path instead of video since the screen branches on the model, not an explicit
  type — was fixed via `testData.copy(imageModel = null)`, with no other call site
  sharing that bug class), and a second, execution-capable validator independently
  re-ran the full layer 1-3 gate with `--rerun-tasks` on every test command and got a
  genuine fresh pass on every one (0 failures on both managed-device test suites).

## Template for new feature/bug tasks

```markdown
## <short title>

- **behavior**: <one sentence — what this makes true that wasn't true before>.
- **verification**: <exact ./gradlew command(s) that prove it, e.g.
  `./gradlew testAndroidHostTest`, or for UI changes
  `./gradlew recordRoborazzi -DIS_DATA_STUB_ENABLED=true` +
  `./gradlew ciVerifyScreenshotJobsMatrixSetup`>.
- **state**: `not_started`.
```
