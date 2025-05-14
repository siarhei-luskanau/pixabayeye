import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

kotlin {
    jvmToolchain(libs.findVersion("build-jvmTarget").get().requiredVersion.toInt())

    androidTarget {
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
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
            implementation(libs.findLibrary("coil-compose").get())
            implementation(libs.findLibrary("coil-network-ktor3").get())
            implementation(libs.findLibrary("jetbrains-lifecycle-viewmodel-compose").get())
            implementation(libs.findLibrary("jetbrains-navigation-compose").get())
            implementation(libs.findLibrary("kotlinx-coroutines-core").get())
            implementation(project.dependencies.platform(libs.findLibrary("coil-bom").get()))
            implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
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
            implementation(libs.findLibrary("androidx-test-core-ktx").get())
            implementation(libs.findLibrary("androidx-uitest-junit4").get())
            implementation(libs.findLibrary("androidx-uitest-testManifest").get())
            implementation(libs.findLibrary("robolectric").get())
            implementation(libs.findLibrary("roborazzi").get())
            implementation(libs.findLibrary("roborazzi-compose").get())
            implementation(libs.findLibrary("roborazzi-rule").get())
        }

        androidInstrumentedTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.findLibrary("androidx-test-core-ktx").get())
            implementation(libs.findLibrary("androidx-test-runner").get())
        }

        jvmMain.dependencies {
            implementation(libs.findLibrary("kotlinx-coroutines-swing").get())
        }

        iosMain.dependencies {
        }

        jsMain.dependencies {
        }
    }
}

android {
    compileSdk = libs.findVersion("build-android-compileSdk").get().requiredVersion.toInt()
    defaultConfig {
        minSdk = libs.findVersion("build-android-minSdk").get().requiredVersion.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(
            libs.findVersion("build-javaVersion").get().requiredVersion
        )
        targetCompatibility = JavaVersion.valueOf(
            libs.findVersion("build-javaVersion").get().requiredVersion
        )
    }
    buildFeatures.compose = true
    packaging.resources.excludes.add("META-INF/**")
}
