plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.common"
    testOptions.configureTestOptions()
}

dependencies {
    ksp(libs.koin.ksp.compiler)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
