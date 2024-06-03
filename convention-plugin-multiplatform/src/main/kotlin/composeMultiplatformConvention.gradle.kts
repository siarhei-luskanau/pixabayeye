import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {

    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(
                        JvmTarget.fromTarget(
                            libs.findVersion("build-jvmTarget").get().requiredVersion
                        )
                    )
                    freeCompilerArgs.add(
                        "-Xjdk-release=${JavaVersion.valueOf(
                            libs.findVersion("build-javaVersion").get().requiredVersion
                        )}"
                    )
                }
            }
        }
    }

    jvm()

    js {
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
        all {
            languageSettings {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.findLibrary("coil3-compose").get())
            implementation(libs.findLibrary("coil3-network-ktor").get())
            implementation(libs.findLibrary("jetbrains-navigation-compose").get())
            implementation(libs.findLibrary("koin-core").get())
            implementation(libs.findLibrary("kotlinx-coroutines-core").get())
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
        }

        androidNativeTest.dependencies {
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
    composeOptions.kotlinCompilerExtensionVersion =
        libs.findVersion("compose-compiler").get().requiredVersion
    packaging.resources.excludes.add("META-INF/**")
}
