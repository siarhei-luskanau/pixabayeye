import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.navigation"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiDetails)
            implementation(projects.ui.uiSearch)
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.ui.uiDebug)
            } else {
                implementation(projects.ui.uiDebugEmpty)
            }
        }
    }
}
