plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.compose.screenshot)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.search"
    testOptions.configureTestOptions()
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
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

dependencies {
    screenshotTestImplementation(libs.cash.paging.compose.common)
}

dependencies {
    ksp(libs.koin.ksp.compiler)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
