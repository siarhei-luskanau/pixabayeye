# Architecture

## Dependency graph

```
core:coreCommon ─────────────────────┐
core:coreNetworkApi ─┬─ core:coreNetworkKtor ──┬─ ui:uiCommon ─┬─ ui:uiMediaList ──┐
                      ├─ core:coreNetworkStub   ├─ ui:uiDebug   ├─ ui:uiMediaDetails│
                      │                         └─ ui:uiDebugEmpty              │
core:coreNetworkDebugLogs / core:coreNetworkDebugEmpty (into coreNetworkKtor)      ├─ navigation ─┬─ composeApp ─┬─ app:androidApp
core:corePref ────────────────────────────────────────────────────────────────────┘               │              ├─ app:desktopApp
core:coreStubResources ── (fixtures for coreNetworkStub) ──────────────────────────────────────────┘              └─ app:webApp
```

Rule: `core → ui → navigation → app` (`composeApp` sits between `navigation`/`ui` and the
`app:*` shells). Nothing in `core` may depend on `ui`, `navigation`, or `app`. This is
enforced only by Gradle module wiring today — there is no lint rule for it, so review
new `implementation(projects.*)` lines against this rule by hand.

## Module reference

| Module | Depends on (project deps) | Purpose |
|---|---|---|
| `core:coreCommon` | — | Per-target dispatchers/platform service (`androidMain`/`iosMain`/`jvmMain`/`webMain` actuals). |
| `core:coreNetworkApi` | — | `PixabayApiService` interface. No implementation — everything else depends only on this. |
| `core:coreNetworkKtor` | coreCommon, coreNetworkApi, corePref, + (coreNetworkDebugLogs \| coreNetworkDebugEmpty) | Real Ktor client implementation of `PixabayApiService`, wired via Koin (`CoreNetworkModule`). |
| `core:coreNetworkStub` | coreNetworkApi, coreStubResources | Fake `PixabayApiService` returning canned JSON. Used for screenshot tests (`-DIS_DATA_STUB_ENABLED=true`) and offline dev. |
| `core:coreNetworkDebugLogs` | Inspektify + Ktor logging | HTTP call inspector, compiled in only when the debug screen is enabled. |
| `core:coreNetworkDebugEmpty` | — | No-op stand-in for `coreNetworkDebugLogs` in release builds — keeps `coreNetworkKtor` buildable without the debug tooling. |
| `core:corePref` | — | DataStore-backed preference storage, Koin module `CorePrefCommonModule`. |
| `core:coreStubResources` | coreNetworkApi | Bundled JSON fixtures consumed by `coreNetworkStub`. |
| `ui:uiCommon` | — | Shared Compose theme/components. |
| `ui:uiMediaList`, `ui:uiMediaDetails` | coreCommon, coreNetworkApi, corePref, uiCommon | One feature screen each: Composable + `*ViewModel` (`@KoinViewModel`) + `*Module.kt` (`@Module`). |
| `ui:uiDebug` | coreCommon, coreNetworkApi, corePref, uiCommon, + (coreNetworkStub \| coreNetworkKtor) | In-app debug/dev-tools screen (datastore inspector, etc.), compiled in only when `isDebugScreenEnabled`. |
| `ui:uiDebugEmpty` | uiCommon | No-op stand-in for `uiDebug` when the debug screen is disabled — keeps `navigation`/`composeApp` buildable either way. |
| `navigation` | coreCommon, uiCommon, the 2 feature UI modules, + (uiDebug \| uiDebugEmpty) | Nav3 (`androidx.navigation3`) graph wiring every screen together, via Material3 Adaptive `NavigationSuiteScaffold` (bottom bar/rail/drawer by window size) and `ListDetailSceneStrategy` (adaptive list/detail panes). Owns Koin module `NavigationCommonModule` and screen chrome (top app bar) that used to live in each feature screen. See "Adaptive navigation" below. |
| `composeApp` | coreCommon, coreNetworkApi, corePref, uiCommon, navigation, the 2 feature UI modules, + (coreNetworkStub \| coreNetworkKtor), + (uiDebug \| uiDebugEmpty) | Composition root: assembles the concrete network/debug variants and hands a ready `App()` composable to each platform shell. Also applies `roborazziConvention` for its own screenshot coverage. |
| `app:androidApp` / `app:webApp` | composeApp | Per-platform entry points (Activity / Wasm `main()`). Thin — no business logic. |
| `app:desktopApp` | composeApp, uiCommon | Per-platform entry point (`main()`). Thin — no business logic. |
| `convention-plugin-multiplatform` (included build) | — | `composeMultiplatformConvention.gradle.kts` (KMP targets, Compose, Koin compiler plugin, Material3 Adaptive, common deps), `androidTestConvention.gradle.kts`, and `roborazziConvention.gradle.kts` (Roborazzi screenshot config + Android `@Preview`-scanning, applied by `uiCommon`/`uiMediaList`/`uiMediaDetails`/`composeApp`). |
| `buildSrc` | — | `LocalPropertiesUtils.kt`: `isDebugScreenEnabled()` / `isDataStubEnabled()` read `local.properties` (or `-D` system props) to pick build-time variants. |

## Why the variant modules exist

**`coreNetworkKtor` vs `coreNetworkStub`**: screenshot tests (Roborazzi/Robolectric) and
offline development need deterministic, network-free data. Rather than mocking inside
`coreNetworkKtor`, the stub is a full sibling implementation of the same
`coreNetworkApi` interface, selected at Gradle configuration time via
`isDataStubEnabled()` (`-DIS_DATA_STUB_ENABLED=true`). Any change to
`PixabayApiService` must be reflected in both implementations or one of them stops
compiling — that's the interface-compatibility constraint from `AGENTS.md`.

**`coreNetworkDebugLogs` vs `coreNetworkDebugEmpty`**, **`uiDebug` vs `uiDebugEmpty`**:
same pattern for the developer-only HTTP inspector and debug screen. Debug tooling
should never ship in a release build, but `coreNetworkKtor`/`navigation`/`composeApp`
still need something to compile against either way — the `*Empty` module is a
zero-dependency stand-in selected via `isDebugScreenEnabled()`.

Both flags are read from `local.properties` by default and can be overridden with
`-DIS_DEBUG_SCREEN_ENABLED=true` / `-DIS_DATA_STUB_ENABLED=true` on the Gradle command
line (this is how CI's screenshot verification job forces the stub variant).

## Adaptive navigation

`navigation` is built on Material3 Adaptive rather than a fixed bottom bar and
single-pane push navigation:

- `AppNavigationSuiteScaffold` wraps `NavigationSuiteScaffold`, choosing the chrome by
  window width: `NavigationDrawer` at ≥1200dp, otherwise
  `NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo` collapsed to
  `NavigationRail`-or-`NavigationBar`. It also owns the top app bar — replacing the
  per-screen `Scaffold`/`PixabayTopAppBar` that `MediaListScreen`/`MediaDetailsScreen`
  used to instantiate themselves.
- `NavApp` wraps its Nav3 `NavDisplay` with `rememberListDetailSceneStrategy`, tagging
  the list route (`ListDetailSceneStrategy.listPane`) and the details route
  (`ListDetailSceneStrategy.detailPane`) so wide windows can show both panes side by
  side (with a placeholder string when nothing is selected), while narrow windows fall
  back to ordinary push navigation.
- `AppNavigation` (the Nav3 `backStack` owner) is now a Koin `@Single` resolved via a
  new `NavigationCommonModule`, rather than being constructed manually inside `NavApp`.

`ui:uiMediaList`/`ui:uiMediaDetails` screens are content-only as a result — they no
longer own a `Scaffold` or navigate directly; new screens should follow the same
pattern and let `navigation` own the chrome.
