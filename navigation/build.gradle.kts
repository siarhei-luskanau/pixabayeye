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
            }
        }
    }
}
