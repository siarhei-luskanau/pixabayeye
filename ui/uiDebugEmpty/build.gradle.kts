plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.debug.empty"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation3.ui)
            implementation(projects.ui.uiCommon)
        }
    }
}
