plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.navigation"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(project(":ui:uiCommon"))
            implementation(project(":ui:uiDetails"))
            implementation(project(":ui:uiLogin"))
            implementation(project(":ui:uiSearch"))
            implementation(project(":ui:uiSplash"))
        }
    }
}
