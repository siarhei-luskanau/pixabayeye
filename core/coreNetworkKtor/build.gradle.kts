import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
}

kotlin.android.namespace = "siarhei.luskanau.pixabayeye.core.network.ktor"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.engine.defaults)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(project.dependencies.platform(libs.ktor.bom))
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.core.coreNetworkDebugLogs)
            } else {
                implementation(projects.core.coreNetworkDebugEmpty)
            }
        }

        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
        }
    }
}

buildConfig {
    packageName(kotlin.android.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val isDebugScreenEnabled = isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }
    buildConfigField("Boolean", "IS_DEBUG_SCREEN_ENABLED", "$isDebugScreenEnabled")
}
