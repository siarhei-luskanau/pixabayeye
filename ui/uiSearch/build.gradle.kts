plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.search"
    // testOptions.configureTestOptions()
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(project(":ui:uiCommon"))
            implementation(libs.lazyPaginationCompose)
        }
        androidUnitTest.dependencies {
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
