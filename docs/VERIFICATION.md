# Verification

Three-layer gate, in order. A cross-module or UI change is not "done" until layer 3
passes — layer 2 passing is not sufficient. This mirrors what `.github/workflows/ci.yml`
already enforces on every push/PR to `main`; nothing here is invented.

## Layer 1 — Static

```
./gradlew ktlintFormat   # auto-fixes style; CI runs this and auto-commits the diff
./gradlew ciLint         # = ktlintCheck + detekt + lint (fails, doesn't auto-fix)
```

Run `ktlintFormat` locally before `ciLint` — don't rely on CI's auto-commit step to
clean up after you.

## Layer 2 — Unit / host tests

```
./gradlew jvmTest              # desktop/JVM target unit tests
./gradlew testAndroidHostTest  # Robolectric host tests, incl. Roborazzi screenshot verify
```

CI runs these (plus `wasmJsBrowserTest`, currently commented out in the matrix) on
every push/PR.

## Layer 3 — Runtime / E2E and platform builds

```
./gradlew managedVirtualDeviceDebugAndroidTest -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
./gradlew managedVirtualDeviceAndroidDeviceTest -Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect
./gradlew iosSimulatorArm64Test   # macOS runner only

./gradlew ciAndroid       # assembleDebug + assembleRelease
./gradlew ciDesktop       # :app:desktopApp:jar
./gradlew ciWasmJsBrowser # :app:webApp:wasmJsMainClasses + wasmJsBrowserDistribution
./gradlew ciIos           # kdoctor + boots an iOS 26 simulator sanity check (macOS only)
```

This layer is what catches defects layer 2 misses on emulators/simulators and real
platform builds — treat it as the actual finish line for anything touching more than
one module or any UI code, not "unit tests are green."

## Screenshot testing (Roborazzi)

```
./gradlew recordRoborazzi -DIS_DATA_STUB_ENABLED=true   # (re)generate reference images
./gradlew testAndroidHostTest                            # verifies against them (default task)
./gradlew ciVerifyScreenshotJobsMatrixSetup               # what CI's VerifyScreenshot matrix runs, per-module
```

Reference images live under each module's `src/screenshots/`. Always pass
`-DIS_DATA_STUB_ENABLED=true` when recording — that forces `coreNetworkStub` so images
are deterministic and don't require network access. Never hand-edit a reference PNG;
regenerate it with `recordRoborazzi` and review the diff.

## Environment sanity check

```
kdoctor
```

Run once per environment (not per session) to confirm JDK 17, Android SDK, and Xcode
are correctly set up — this is a standalone CLI tool, not a Gradle task. See
`README.MD` for install/setup.

## CI jobs, for reference

`Lint` → `Tests` (matrix: `jvmTest`, `testAndroidHostTest`, Android emulator tests,
`iosSimulatorArm64Test`) → `VerifyScreenshotMatrixSetup` + `VerifyScreenshot` → parallel
`Android` / `Desktop` / `WasmJsBrowser` / `iOS` release-build jobs. All run on every
push/PR to `main`; `workflow_dispatch` allows manual triggering.

## Maker/checker routing

An implementer (junior/middle/senior-backend-engineer, per the global agent roster)
never flips a `TASKS.md` entry to `passing` on its own run — that's a self-certification
and doesn't count. A validator (`middle-code-validator` for standard changes,
`senior-code-validator` for complex/interdependent ones) re-runs the task's listed
verification command(s) independently and only then marks it `passing`.

Which layer the validator must run is not optional:

- Change confined to one module, no UI: validator confirming layer 2 is sufficient.
- Change touching more than one module, or any UI code: validator must run layer 3.
  A validator report that only reached layer 2 does not authorize `passing` for these —
  send it back, don't downgrade the requirement.

This is the existing generator/evaluator split from the global orchestrator config,
routed explicitly at the layer that matters for this repo — not a separate mechanism.
