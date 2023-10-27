plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.ui.details"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coreCommon"))
                implementation(project(":core:coreNetwork"))
                implementation(project(":core:corePref"))
                implementation(libs.qdsfdhvh.image.loader)
            }
        }
    }
}
