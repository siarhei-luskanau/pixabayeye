import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("com.android.application")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("testOptionsConvention")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.build.jvmTarget.get()
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
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(project(":navigation"))
            implementation(libs.koin.core)
            implementation(libs.okio)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        androidNativeTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.test.runner)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
        }

        iosMain.dependencies {
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.ui)
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
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    packaging.resources.excludes.add("META-INF/**")
    testOptions.configureTestOptions()
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "siarhei.luskanau.compose.multiplatform.pixabayeye.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}
