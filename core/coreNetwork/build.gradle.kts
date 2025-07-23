import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.kotlinx.serialization)
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.network"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(projects.core.coreCommon)
            implementation(projects.core.corePref)
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.core.coreNetworkDebugLogs)
            } else {
                implementation(projects.core.coreNetworkDebugEmpty)
            }
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js.wasm.js)
        }
    }
}
