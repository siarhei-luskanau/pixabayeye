import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.build.jvmTarget.get()
            }
        }
    }

    jvm("desktop")

    js {
        browser()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "11.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coreCommon"))
                implementation(project(":core:coreNetwork"))
                implementation(project(":core:corePref"))
                implementation(project(":navigation"))
                implementation(libs.koin.core)
                implementation(libs.okio)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.androidx.test.core.ktx)
                implementation(libs.androidx.test.runner)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.ui)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
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
