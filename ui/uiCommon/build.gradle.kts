plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.compose.screenshot)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.common"
    testOptions.configureTestOptions()
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

compose.resources {
    publicResClass = true
    packageOfResClass = "siarhei.luskanau.pixabayeye.ui.common.resources"
    generateResClass = always
}
