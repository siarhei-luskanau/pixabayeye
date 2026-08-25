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
  behavior/verification/state fields, per `HARNESS_IMPROVEMENT_PLAN.md`.
- **verification**: n/a (docs-only change; no Gradle command applies).
- **state**: `passing` (file created and linked from `AGENTS.md`; docs-only changes
  have no build verification, so existence + link is the completion signal).

## Harness plan Phase 4 — Verification tiering explicit in maker/checker routing

- **behavior**: confirm `docs/VERIFICATION.md`'s 3-layer gate is what implementers and
  validators both route through, and that layer 3 (not layer 2) is required before any
  cross-module/UI change is marked `passing` here.
- **verification**: n/a (docs-only; `docs/VERIFICATION.md` already states the rule
  explicitly as of Phase 1).
- **state**: `not_started`.

## Harness plan Phase 5 — Dependabot triage loop

- **behavior**: stand up a `/loop` (or scheduled cloud agent) that merges Dependabot
  PRs on green CI + patch/minor semver, and flags major bumps or red CI for human
  review, per `HARNESS_IMPROVEMENT_PLAN.md` Phase 5.
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
