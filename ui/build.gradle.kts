plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.ui"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":network"))
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
