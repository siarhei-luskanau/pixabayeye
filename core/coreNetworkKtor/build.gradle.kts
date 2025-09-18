import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.network.ktor"
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
            implementation(projects.core.coreNetworkApi)
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

        webMain.dependencies {
            implementation(libs.ktor.client.js.wasm.js)
        }
    }
}

buildConfig {
    packageName(android.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val isDebugScreenEnabled = isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }
    buildConfigField("Boolean", "IS_DEBUG_SCREEN_ENABLED", "$isDebugScreenEnabled")
}
