plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.ui"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(project(":network"))
                implementation(project(":pref"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(libs.cash.paging.compose.common)
                // implementation(libs.decompose)
                // implementation(libs.decompose.extensions.compose.jetbrains)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.qdsfdhvh.image.loader)
            }
        }
    }
}
