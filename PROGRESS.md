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

- Implementing the Harness Engineering Improvement Plan phase by phase (Phase 0 and
  Phase 1 already landed: `docs/ARCHITECTURE.md`, `docs/VERIFICATION.md`,
  `docs/CONVENTIONS.md`, `AGENTS.md`). Currently on Phase 2 (state & continuity):
  this file, `DECISIONS.md`, and the Session Exit Checklist / clock-in routine in
  `AGENTS.md`.
- No blockers.

## Next steps

- Phase 3: add `TASKS.md` with per-task behavior/verification/state fields and a
  WIP=1 rule in `AGENTS.md`.
- Phase 4: fold the 3-layer verification gate explicitly into the maker/checker
  routing (already partly done in `docs/VERIFICATION.md`).
- Phase 5: stand up the Dependabot triage loop.
