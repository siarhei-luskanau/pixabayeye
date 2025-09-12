plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.debug.empty"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.navigation3.ui)
            implementation(projects.ui.uiCommon)
        }
    }
}
