# Conventions

## Koin DI via annotations (KSP) — never manual modules

DI wiring is generated at compile time by `io.insert-koin.compiler.plugin` (applied in
`composeMultiplatformConvention`). There is no hand-written `module { single { ... } }`
DSL anywhere in this repo — don't add one.

Per-feature module, one per UI/core module, scanning its own package:

```kotlin
// ui/uiMediaList/.../UiMediaListModule.kt
@Module
@ComponentScan(value = ["siarhei.luskanau.pixabayeye.ui.media.list"])
class UiMediaListModule
```

ViewModels use `@KoinViewModel`. Constructor params injected at call-site (e.g. a
navigation callback, an initial search term) are `@InjectedParam`; params resolved from
the DI graph are `@Provided`:

```kotlin
// ui/uiMediaList/.../MediaListViewModel.kt
@KoinViewModel
class MediaListViewModel(
    @InjectedParam private val mediaType: MediaType,
    @InjectedParam private val mediaListNavigationCallback: MediaListNavigationCallback,
    @InjectedParam initialSearchTerm: String?,
    @Provided private val pixabayApiService: PixabayApiService
)
```

The app-level composition root (`composeApp/.../AppKoinApplication.kt`) collects every
feature `*Module` under one `@KoinApplication(modules = [...])`. When adding a new
feature module, register its `*Module::class` here — it will not be picked up
automatically.

`composeApp/build.gradle.kts` disables `koinCompiler { compileSafety = false }`
specifically because iOS's `Koin.get(ObjCClass)` Swift-interop bridge resolves by
dynamic `KClass<*>` and always false-positives on the full-graph checker. The DI graph
is still statically validated on the JVM/Android compiles — don't re-enable
`compileSafety` to "fix" the iOS false positive; that's the known, accepted state.

## Convention plugins

`convention-plugin-multiplatform` is an included build (`settings.gradle.kts` →
`includeBuild`), not a published artifact. It defines:

- `composeMultiplatformConvention` — applied by every KMP module. Sets up Android/JVM/
  iOS/Wasm targets, Compose, the Koin compiler plugin, and the common dependency set
  (Coil, Compose, Koin, coroutines, Material3 Adaptive). Also configures the
  `androidHostTest` task to exclude `*CommonTest*` and not fail the build when a module
  has no discovered tests.
- `androidTestConvention` — applied where Android instrumentation/managed-device tests
  run (currently `composeApp`).
- `roborazziConvention` — applied by `ui:uiCommon`, `ui:uiMediaList`, `ui:uiMediaDetails`,
  and `composeApp`. Sets Roborazzi's `outputDir` (each module's own `src/screenshots`)
  and wires the JVM/iOS Robolectric test deps plus the experimental
  `generateComposePreviewRobolectricTests` preview scanner. See "Screenshot tests" below.

If a module needs a dependency only it uses, add it in that module's own
`build.gradle.kts` `sourceSets { ... }` block — don't add it to the convention plugin
unless every module genuinely needs it.

Build-time variant selection (`isDebugScreenEnabled()`, `isDataStubEnabled()`) lives in
`buildSrc/src/main/kotlin/LocalPropertiesUtils.kt` and reads the `-D` Gradle system
property first, falling back to `local.properties` only if the system property isn't
set. See `docs/ARCHITECTURE.md` for why these variants exist.

## ktlint

Rules come from `ktlint.gradle` at repo root (registers `ktlintCheck`/`ktlintFormat` as
plain `JavaExec` tasks running the Pinterest ktlint CLI — not the Gradle ktlint plugin).
Scope is `src/**/*.kt`, `buildSrc/**/*.kt`, `convention-plugin-multiplatform/**/*.kt`,
and `**.kts`, excluding `**/build/**`. `ktlint.gradle` also references a
`convention-plugin-test-option` path (inconsistently between its two tasks —
`ktlintCheck` uses the bare directory name, `ktlintFormat` uses
`convention-plugin-test-option/**/*.kt`) but no such directory exists anywhere in this
repo, so that reference is currently dead; don't create a module by that name just to
satisfy it. No custom `.editorconfig` rule overrides beyond what's in `.editorconfig` at
repo root — check there before assuming a formatting choice is a ktlint quirk.

`detekt` also runs (via `ciLint`) with `ignoreFailures = false` and `parallel = true`,
applied to `allprojects` in the root `build.gradle.kts`.

## Screenshot tests (Roborazzi)

Screenshot config lives in the shared `roborazziConvention` plugin (each module gets its
own `src/screenshots` output dir), applied by `ui:uiCommon`, `ui:uiMediaList`,
`ui:uiMediaDetails`, and `composeApp` — not set ad hoc per module.

- **Android**: fully automated. Roborazzi's experimental Compose Preview scanner
  (`generateComposePreviewRobolectricTests`, `@OptIn(ExperimentalRoborazziApi::class)`)
  scans each module's own `@Preview` composables and generates the `androidHostTest`
  (Robolectric) tests at build time — there are no hand-written `*AndroidTest.kt`
  screenshot files anywhere in the repo. To add Android screenshot coverage, add or
  adjust an `@Preview`; don't write a test class. Day/night coverage comes from paired
  `@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO/YES)` functions; generated PNGs are
  named `<FileKt>.<PreviewFunctionName>.<DAY|NIGHT>.png`.
- **JVM/iOS**: still hand-written. `*JvmTest.kt`/`*IosTest.kt` classes (e.g.
  `MediaListScreenJvmTest.kt`, `MediaListScreenIosTest.kt`) call
  `onRoot().captureRoboImage(...)` explicitly per preview — the preview scanner doesn't
  cover these targets.

See `docs/VERIFICATION.md` for the record/verify commands — always record with
`-DIS_DATA_STUB_ENABLED=true` so images are generated against the deterministic stub
network, not live Pixabay data.
