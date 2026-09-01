import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android.namespace = "siarhei.luskanau.pixabayeye.navigation"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(projects.core.coreCommon)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiMediaDetails)
            implementation(projects.ui.uiMediaList)
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.ui.uiDebug)
            } else {
                implementation(projects.ui.uiDebugEmpty)
            }
        }
    }
}
