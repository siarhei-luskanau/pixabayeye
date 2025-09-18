plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.debug.empty"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.ui.uiCommon)
        }
    }
}
