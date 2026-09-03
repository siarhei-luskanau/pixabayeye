# Decisions

Architectural/dependency decisions with rationale and rejected alternatives. Add an
entry whenever a decision would otherwise have to be re-derived from git archaeology.
Newest first.

## Material3 Adaptive `NavigationSuiteScaffold` + `ListDetailSceneStrategy` instead of a fixed bottom bar

**Decision**: `bf18234` "feat(navigation): migrate to Material 3 Adaptive Navigation" replaced the fixed `PixabayBottomBar`
(deleted, along with its tests) with Material3 Adaptive's `NavigationSuiteScaffold`
(bottom bar / nav rail / nav drawer, chosen by window width — drawer at ≥1200dp, else
`calculateFromAdaptiveInfo` collapsed to rail-or-bar) wrapping a Nav3 `NavDisplay` that
uses `rememberListDetailSceneStrategy` for adaptive list/detail panes. Screen chrome
(top app bar, nav bar/rail/drawer) moved out of `MediaListScreen`/`MediaDetailsScreen`
and up into a new `AppNavigationSuiteScaffold` at the `NavApp` composition root;
`navigation` gained its own Koin module (`NavigationCommonModule`) since `AppNavigation`
became a `@Single` Koin-managed class instead of being constructed manually in
`NavApp.kt`. See `docs/ARCHITECTURE.md`'s "Adaptive navigation" section.

**Why**: a fixed bottom bar and single-pane push navigation don't use the extra space
on tablets/desktop/web; Material3 Adaptive is Compose Multiplatform's supported answer
to that, and Nav3's list-detail scene strategy lets wide windows show the media list and
its detail pane side by side without a second navigation model.

**Consequence**: `MediaListScreen`/`MediaDetailsScreen` no longer own a `Scaffold`; they
are content-only composables now, meant to be hosted inside `AppNavigationSuiteScaffold`
+ `NavDisplay`. Don't reintroduce a per-screen `Scaffold`/top-bar when adding new
screens — wire them through the shared scaffold instead.

## Roborazzi Compose Preview scanner instead of hand-written Android screenshot tests

**Decision**: `d0c77ae` "test: migrate to automated preview-based Roborazzi
screenshots" replaced hand-written `*AndroidTest.kt` Roborazzi test classes with
Roborazzi's experimental Compose Preview scanner
(`generateComposePreviewRobolectricTests`, `@OptIn(ExperimentalRoborazziApi::class)`),
which generates `androidHostTest` (Robolectric) tests directly from each module's own
`@Preview` composables. Config moved out of individual modules into a new shared
`roborazziConvention.gradle.kts` plugin (applied by `uiCommon`, `uiMediaList`,
`uiMediaDetails`, `composeApp`).

**Why**: removes the duplication of maintaining a `@Preview` and a matching
`*AndroidTest.kt` by hand for every composable that needs Android screenshot coverage —
adding coverage is now "add an `@Preview`," not "add a test class too."

**Scope**: Android-only. JVM (`*JvmTest.kt`) and iOS (`*IosTest.kt`) screenshot tests
are still hand-written per preview — the scanner doesn't cover those targets. See
`docs/CONVENTIONS.md`'s Screenshot tests section.

## Koin DI: compile-time annotations (koin-annotations/KSP), not manual `module {}`

**Decision**: DI wiring is generated via `io.insert-koin.compiler.plugin`
(`@Module`/`@ComponentScan`/`@KoinViewModel`/`@Single`), applied in
`composeMultiplatformConvention`. See `docs/CONVENTIONS.md` for the pattern.

**History**: this repo tried both directions before landing here —
`f21d8cb` "Used Koin Compose DI" → `3c3cff2` "Remove Koin KSP and use manual DI" (manual
`module { single { ... } }` DSL) → `dee7a04` "Migrate Koin DI to compile-time
annotations (koin-annotations/KSP)" (current state). The commit messages don't record
*why* KSP was removed the first time; treat the current annotations-based setup as the
settled choice and don't re-attempt a manual-DSL rollback without checking why the
first KSP attempt was reverted.

**Known accepted rough edge**: `composeApp/build.gradle.kts` sets
`koinCompiler { compileSafety = false }` because iOS's `Koin.get(ObjCClass)`
Swift-interop bridge resolves by dynamic `KClass<*>` and always false-positives the
full-graph checker. JVM/Android compiles still statically validate the graph. Don't
re-enable `compileSafety` to "fix" this — it's the known, accepted state.

## Roborazzi (not Paparazzi) for screenshot testing

**Decision**: `9b5f9f0` "Add Roborazzi screenshot testing for Android, Jvm, iOS
targets" — Roborazzi + Robolectric, running inside `androidHostTest`, not Paparazzi.

**Why (inferred from what the setup requires, not stated in the commit)**: the project
needed screenshot coverage across Android, JVM/Desktop, and iOS targets from one KMP
test source set. Roborazzi's Robolectric-hosted approach runs on the JVM without a
device/emulator, which suits `testAndroidHostTest`'s host-test model. Paparazzi is
Android-only and wasn't evaluated in-repo (no rejected-Paparazzi commit exists) — this
line is an inference, not a confirmed rejection; don't over-cite it as settled history.

**Consequence**: reference images are deterministic only when the network layer is; see
next entry.

## Four `coreNetwork*` variant modules instead of runtime mocking

**Decision**: `7995362` "Refactor: Introduce network stub and API modules" split
`coreNetworkApi` (interface) from `coreNetworkKtor` (real) and added `coreNetworkStub`
(fake, backed by `coreStubResources`), selected at Gradle-configuration time via
`isDataStubEnabled()` rather than mocked inside `coreNetworkKtor` at runtime.

**Why**: Roborazzi screenshot tests need deterministic, network-free responses.
Compile-time module swapping keeps `coreNetworkKtor` free of test-only branching and
forces both implementations to satisfy the same `PixabayApiService` interface — a
change to the interface breaks the build immediately in whichever implementation wasn't
updated, instead of failing silently at runtime.

**Rejected alternative**: mocking the network client inside `coreNetworkKtor` — not
used, because it would couple production code to test doubles instead of keeping them
as an interface-compatible sibling module.

## Debug tooling (`coreNetworkDebugLogs`/`Empty`, `uiDebug`/`uiDebugEmpty`) as swappable modules

**Decision**: `c5cbd2a` "Moved Ktor logging and Inspektify to the debug module. Added
Debug screen." isolated the Inspektify HTTP inspector and the in-app debug screen into
their own modules, each with a no-op `*Empty` sibling selected via
`isDebugScreenEnabled()`.

**Why**: debug tooling must never ship in a release build, but `coreNetworkKtor`,
`navigation`, and `composeApp` still need something to compile against regardless of
which variant is active — same interface-compatible-sibling pattern as the network
stub, applied to debug-only code instead of test-only code.

## Not yet decided / open

- Why the first Koin-KSP attempt (`3c3cff2`) was reverted to manual DI before being
  re-introduced (`dee7a04`) is not recorded anywhere in git history. If this comes up
  again, don't assume the annotations approach is fragile — the revert predates the
  current, apparently stable, `koin-annotations` setup.
