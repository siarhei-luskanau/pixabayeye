plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.common"
    testOptions.configureTestOptions()
}

compose.resources {
    publicResClass = true
    packageOfResClass = "siarhei.luskanau.pixabayeye.ui.common.resources"
    generateResClass = always
}
