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

- Implementing the harness improvement plan phase by phase (Phase 0, 1, 2, and 3
  landed and committed: `docs/ARCHITECTURE.md`, `docs/VERIFICATION.md`,
  `docs/CONVENTIONS.md`, `AGENTS.md`, `DECISIONS.md`, `TASKS.md`, this file). Phase 4
  landed this session: `docs/VERIFICATION.md` gained an explicit "Maker/checker
  routing" section (implementers never self-certify `passing`; a validator's
  independent re-run does, and must reach layer 3 for cross-module/UI changes),
  cross-linked from `AGENTS.md`'s Task tracking section.
- No blockers. Working tree not yet committed (file changes only this session, no
  commit).

## Next steps

- Phase 5: stand up the Dependabot triage loop; see `TASKS.md`.
- Phase 6: schedule the monthly harness review cadence; see `TASKS.md`.
