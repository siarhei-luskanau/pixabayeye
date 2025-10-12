import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.compose.ExperimentalComposeLibrary

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.kotlin.multiplatform.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

kotlin {
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())

    androidLibrary {
        compileSdk = libs.versions.build.android.compileSdk.get().toInt()
        minSdk = libs.versions.build.android.minSdk.get().toInt()
        androidResources.enable = true
        withJava()
        withHostTestBuilder {}.configure {
            isIncludeAndroidResources = true
            enableCoverage = true
        }
    }

    jvm()

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
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(compose.ui)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.jetbrains.lifecycle.viewmodel.compose)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project.dependencies.platform(libs.coil.bom))
            implementation(project.dependencies.platform(libs.koin.bom))
        }

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
        }

        androidUnitTest.dependencies {
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.uitest.junit4)
            implementation(libs.androidx.uitest.testManifest)
            implementation(libs.robolectric)
            implementation(libs.roborazzi)
            implementation(libs.roborazzi.compose)
            implementation(libs.roborazzi.rule)
        }

        androidInstrumentedTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.test.runner)
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }

        iosMain.dependencies {
        }

        webMain.dependencies {
        }
    }
}

tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}
