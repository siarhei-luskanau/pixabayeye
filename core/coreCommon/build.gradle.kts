plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.core.common"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
