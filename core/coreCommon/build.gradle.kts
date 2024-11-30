plugins {
    id("composeMultiplatformKspConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.common"
    testOptions.configureTestOptions()
}
