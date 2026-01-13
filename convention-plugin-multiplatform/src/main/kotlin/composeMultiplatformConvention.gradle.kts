import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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
        withHostTestBuilder {}.configure {
            isIncludeAndroidResources = true
            enableCoverage = true
        }
        packaging.resources.excludes.add("META-INF/**")
    }

    jvm()

    wasmJs { browser() }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.jetbrains.compose.animation)
            implementation(libs.jetbrains.compose.animation.graphics)
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material.icons.extended)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
            implementation(libs.jetbrains.lifecycle.viewmodel.navigation3)
            implementation(libs.jetbrains.savedstate.compose)
            implementation(libs.jetbrains.window.core)
            implementation(libs.koin.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project.dependencies.platform(libs.coil.bom))
            implementation(project.dependencies.platform(libs.koin.bom))
        }

        commonTest.dependencies {
            implementation(libs.jetbrains.compose.ui.test)
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
        }

        getByName("androidHostTest") {
            dependencies {
                implementation(libs.androidx.uitest.junit4)
                implementation(libs.androidx.uitest.testManifest)
                implementation(libs.robolectric)
                implementation(libs.roborazzi)
                implementation(libs.roborazzi.compose)
                implementation(libs.roborazzi.rule)
            }
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }

        iosMain.dependencies {
        }

        webMain.dependencies {
        }
    }

    targets
        .withType<KotlinNativeTarget>()
        .matching { it.konanTarget.family.isAppleFamily }
        .configureEach {
            binaries { framework { baseName = "ComposeApp" } }
        }
}

tasks.withType<AbstractTestTask>().configureEach {
    failOnNoDiscoveredTests = false
}
