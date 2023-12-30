plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.details"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(libs.qdsfdhvh.image.loader)
        }
    }
}
