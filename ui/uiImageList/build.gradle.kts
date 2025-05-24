plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.image.list"
    // testOptions.configureTestOptions()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.lazyPaginationCompose)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidUnitTest.dependencies {
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
