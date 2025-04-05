plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.common"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        jvmMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }

        androidMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "siarhei.luskanau.pixabayeye.ui.common.resources"
    generateResClass = always
}
