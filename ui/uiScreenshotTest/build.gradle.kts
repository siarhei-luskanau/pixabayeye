plugins {
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.screenshot.test"
    sourceSets {
        androidMain.dependencies {
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
