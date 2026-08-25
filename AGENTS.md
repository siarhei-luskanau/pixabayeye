# PixabayEye — Project Router

Kotlin Multiplatform (Android/iOS/Desktop/Web via Wasm) image/video search app on the
Pixabay API. Compose Multiplatform UI, Ktor networking, Koin DI via compile-time
annotations (KSP), custom Gradle convention plugins.

This file is a router, not an encyclopedia. Read the linked doc only when the task
touches that area.

## Module map

- `core:coreCommon` — shared platform utilities (dispatchers, platform service) per target.
- `core:coreNetworkApi` — `PixabayApiService` interface only, no implementation.
- `core:coreNetworkKtor` — real Ktor-based implementation of `coreNetworkApi`.
- `core:coreNetworkStub` — fake implementation backed by `coreStubResources`, for screenshot tests / offline dev.
- `core:coreNetworkDebugLogs` / `core:coreNetworkDebugEmpty` — pluggable HTTP logging (Inspektify); Logs when the debug screen is enabled, Empty otherwise.
- `core:corePref` — DataStore-backed preferences.
- `core:coreStubResources` — canned JSON fixtures consumed by `coreNetworkStub`.
- `navigation` — Nav3-based app graph; wires the four feature UI modules plus `uiDebug`/`uiDebugEmpty`.
- `ui:uiCommon` — shared Compose components/theme; owns the Roborazzi screenshot infra.
- `ui:uiImageList`, `ui:uiImageDetails`, `ui:uiVideoList`, `ui:uiVideoDetails` — feature screens.
- `ui:uiDebug` / `ui:uiDebugEmpty` — in-app debug/dev-tools screen; Debug when enabled, Empty otherwise (same swap pattern as network).
- `app:androidApp`, `app:desktopApp`, `app:webApp` — per-platform app shells.
- `composeApp` — shared app composition root wiring `navigation` + all UI + network variant; consumed by each `app:*` shell.
- `convention-plugin-multiplatform` (included build) — `composeMultiplatformConvention` and `androidTestConvention` Gradle plugins every module applies.
- `buildSrc` — shared build logic (`LocalPropertiesUtils.kt` defines `isDebugScreenEnabled` / `isDataStubEnabled`).

Full dependency graph and the reasoning behind the swappable variants: `docs/ARCHITECTURE.md`.

## Running the app

Per-platform run/dev commands (Android Studio config, `:app:desktopApp:run`, Xcode
project, `wasmJsBrowserDevelopmentRun`) are in `README.MD` under "Running the App" —
not duplicated here.

## Dependency direction (forward-only)

`core → ui → navigation → app` (`composeApp` sits between `navigation`/`ui` and `app:*`).
Never add a dependency that points backward (e.g. `core` depending on `ui`).

## Hard constraints

- ktlint (`ktlintFormat`) must be clean; CI auto-commits formatting on `main`/PRs, but don't rely on that locally.
- Koin DI uses `koin-annotations` + KSP compiler plugin (`@Single`, `@KoinViewModel`, `*Module.kt` per feature) — never hand-write a manual `module { }` DSL block.
- Screenshot tests use Roborazzi + Robolectric (`androidHostTest` source set); reference images live in each module's `src/screenshots`.
- The four `coreNetwork*` and two `uiDebug*` variants must stay interface-compatible with each other — they're swapped at build time via `local.properties` flags, not chosen at runtime.
- Module boundaries in `settings.gradle.kts` are the source of truth for what's buildable; don't invent module names.

## Verification

Exact commands and the 3-layer gate (static → unit/host → runtime/E2E) are in
`docs/VERIFICATION.md`. Never call a cross-module or UI change "done" on unit tests alone.

## Conventions

Koin annotation patterns, convention-plugin usage, ktlint rule source: `docs/CONVENTIONS.md`.

## Task tracking

Per-task behavior/verification/state entries live in `TASKS.md`. A task's `state` only
becomes `passing` after its listed verification command actually succeeds — never by
self-assessment. Concretely: an implementer runs verification to check its own work,
but only a validator's independent re-run flips `state` to `passing` — see the
maker/checker routing in `docs/VERIFICATION.md`. For any task touching more than one
module or any UI code, that re-run must reach layer 3; a validator that only ran layer
2 does not authorize `passing`.

**WIP = 1**: exactly one task may be `active` at a time. Finish or explicitly mark it
`blocked` (with a reason) before starting another — no "while I'm here" scope creep into
a second task or an unrelated module.

## Clock-in routine (start of session)

1. Read `PROGRESS.md` (current state, in-progress work, next steps) and
   `DECISIONS.md` (don't re-litigate a settled decision or repeat a rejected
   alternative).
2. Run `./gradlew jvmTest` to confirm a consistent, green starting state before
   making changes. If it's red, that's the actual starting point — fix or note it in
   `PROGRESS.md` before starting new work, don't build on top of an unverified state.

## Session Exit Checklist (end of session)

Before ending a session, confirm all of the following:

- [ ] `./gradlew ktlintFormat` clean.
- [ ] `./gradlew jvmTest` and `./gradlew testAndroidHostTest` pass.
- [ ] `PROGRESS.md` updated (current state, verified-green state, in-progress/blockers,
      next steps).
- [ ] No stray debug artifacts (temp files, commented-out code, leftover
      `println`/log statements added for debugging).
- [ ] Working tree committed or explicitly stashed with a note — never left dirty and
      undocumented.
