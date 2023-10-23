plugins {
    composeMultiplatformConvention
}

android.namespace = "siarhei.luskanau.pixabayeye.navigation"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coreCommon"))
                implementation(project(":core:coreNetwork"))
                implementation(project(":core:corePref"))
                implementation(project(":ui:uiDetails"))
                implementation(project(":ui:uiLogin"))
                implementation(project(":ui:uiSearch"))
                implementation(project(":ui:uiSplash"))
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
