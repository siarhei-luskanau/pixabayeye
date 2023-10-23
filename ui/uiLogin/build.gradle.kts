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
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                // implementation(libs.decompose)
                // implementation(libs.decompose.extensions.compose.jetbrains)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
