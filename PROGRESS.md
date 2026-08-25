# Progress

Update this at the end of every session (see Session Exit Checklist in `AGENTS.md`).
This file answers: what branch/commit, what's verified, what's in flight, what's next.

## Current state

- Branch: `harness`, on top of `main` (up to date with `origin/harness`).
- Last commit: `f5de83f` "docs: add Harness Engineering Improvement Plan".
- Working tree: clean.

## Last verified-green state

- `./gradlew jvmTest` — passed (2026-08-24, this session).
- Layers 1 (`ktlintFormat`/`ciLint`) and 3 (E2E/platform builds) not re-run this
  session — don't assume they're green without running them; see
  `docs/VERIFICATION.md`.

## In progress

- Implementing the Harness Engineering Improvement Plan phase by phase (Phase 0, 1,
  and 2 landed: `docs/ARCHITECTURE.md`, `docs/VERIFICATION.md`, `docs/CONVENTIONS.md`,
  `AGENTS.md`, `DECISIONS.md`, this file). Phase 3 (task tracking) landed this
  session: `TASKS.md` with per-task behavior/verification/state fields and a WIP=1
  rule in `AGENTS.md`.
- No blockers. Working tree not yet committed (see task's instructions: file changes
  only this session, no commit).

## Next steps

- Phase 4: fold the 3-layer verification gate explicitly into the maker/checker
  routing (already partly done in `docs/VERIFICATION.md`); see `TASKS.md`.
- Phase 5: stand up the Dependabot triage loop; see `TASKS.md`.
- Phase 6: schedule the monthly harness review cadence; see `TASKS.md`.
