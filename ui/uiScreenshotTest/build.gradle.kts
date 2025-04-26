plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.uitest.junit4)
            implementation(libs.robolectric)
            implementation(libs.roborazzi)
            implementation(libs.roborazzi.compose)
            implementation(libs.roborazzi.rule)
        }
        androidUnitTest.dependencies {
            implementation(libs.robolectric)
        }
    }
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.screenshot.test"
    testOptions.configureTestOptions()
}
