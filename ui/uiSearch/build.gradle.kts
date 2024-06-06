plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.search"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(project(":ui:uiCommon"))
            implementation(libs.cash.paging.compose.common)
        }
    }
}
