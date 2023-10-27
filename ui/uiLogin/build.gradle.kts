plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.ui.login"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coreCommon"))
                implementation(project(":core:coreNetwork"))
                implementation(project(":core:corePref"))
            }
        }
    }
}
