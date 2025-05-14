import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.hotReload)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.multiplatform)
    id("testOptionsConvention")
}

kotlin {
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())

    androidTarget {
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("io.insert-koin:koin-compose") {
                exclude(module = "koin-core-annotations-jvm", group = "io.insert-koin")
            }
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.jetbrains.navigation.compose)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.navigation)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiDetails)
            implementation(projects.ui.uiSearch)
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.ui.uiDebug)
            } else {
                implementation(projects.ui.uiDebugEmpty)
            }
        }

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.datastore.core.okio)
        }

        androidInstrumentedTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.test.runner)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.androidx.datastore.core.okio)
        }

        iosMain.dependencies {
        }

        jsMain.dependencies {
        }
    }
}

android {
    namespace = "siarhei.luskanau.compose.multiplatform.pixabayeye"
    compileSdk = libs.versions.build.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.build.android.minSdk.get().toInt()
        targetSdk = libs.versions.build.android.targetSdk.get().toInt()
        applicationId = "siarhei.luskanau.compose.multiplatform.pixabayeye.androidApp"
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
    }
    buildFeatures.compose = true
    packaging.resources.excludes.add("META-INF/**")
    testOptions.configureTestOptions()
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PixabayEye"
            packageVersion = "1.0.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "org.company.app.desktopApp"
            }
        }
    }
}

// https://github.com/JetBrains/compose-hot-reload
composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
tasks.withType<ComposeHotRun>().configureEach {
    mainClass.set("MainKt")
}

dependencies {
    // https://developer.android.com/develop/ui/compose/testing#setup
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)

    // KSP Tasks
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspJs", libs.koin.ksp.compiler)
    add("kspJvm", libs.koin.ksp.compiler)
    debugImplementation(libs.leakcanary.android)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
