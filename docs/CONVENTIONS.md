# Conventions

## Koin DI via annotations (KSP) — never manual modules

DI wiring is generated at compile time by `io.insert-koin.compiler.plugin` (applied in
`composeMultiplatformConvention`). There is no hand-written `module { single { ... } }`
DSL anywhere in this repo — don't add one.

Per-feature module, one per UI/core module, scanning its own package:

```kotlin
// ui/uiImageList/.../UiImageListModule.kt
@Module
@ComponentScan(value = ["siarhei.luskanau.pixabayeye.ui.image.list"])
class UiImageListModule
```

ViewModels use `@KoinViewModel`. Constructor params injected at call-site (e.g. a
navigation callback, an initial search term) are `@InjectedParam`; params resolved from
the DI graph are `@Provided`:

```kotlin
// ui/uiImageList/.../ImageListViewModel.kt
@KoinViewModel
class ImageListViewModel(
    @InjectedParam private val imageListNavigationCallback: ImageListNavigationCallback,
    @InjectedParam initialSearchTerm: String?,
    @Provided private val pixabayApiService: PixabayApiService,
    ...
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
  (Coil, Compose, Koin, coroutines). Also configures the `androidHostTest` task to
  exclude `*CommonTest*` and not fail the build when a module has no discovered tests.
- `androidTestConvention` — applied where Android instrumentation/managed-device tests
  run (currently `composeApp`).

If a module needs a dependency only it uses, add it in that module's own
`build.gradle.kts` `sourceSets { ... }` block — don't add it to the convention plugin
unless every module genuinely needs it.

Build-time variant selection (`isDebugScreenEnabled()`, `isDataStubEnabled()`) lives in
`buildSrc/src/main/kotlin/LocalPropertiesUtils.kt` and reads `local.properties` first,
falling back to `-D` Gradle system properties. See `docs/ARCHITECTURE.md` for why these
variants exist.

## ktlint

Rules come from `ktlint.gradle` at repo root (registers `ktlintCheck`/`ktlintFormat` as
plain `JavaExec` tasks running the Pinterest ktlint CLI — not the Gradle ktlint plugin).
Scope is `src/**/*.kt`, `buildSrc/**/*.kt`, `convention-plugin-multiplatform/**/*.kt`,
`convention-plugin-test-option/**/*.kt`, and `**.kts`, excluding `**/build/**`. No
custom `.editorconfig` rule overrides beyond what's in `.editorconfig` at repo root —
check there before assuming a formatting choice is a ktlint quirk.

`detekt` also runs (via `ciLint`) with `ignoreFailures = false` and `parallel = true`,
applied to `allprojects` in the root `build.gradle.kts`.

## Screenshot tests (Roborazzi + Robolectric)

`ui:uiCommon` sets `roborazzi.outputDir.set(file("src/screenshots"))`; `composeApp` sets
its own equivalent. Screenshot tests run inside the `androidHostTest` source set
(Robolectric), not as instrumented Android tests. See `docs/VERIFICATION.md` for the
record/verify commands — always record with `-DIS_DATA_STUB_ENABLED=true` so images are
generated against the deterministic stub network, not live Pixabay data.
