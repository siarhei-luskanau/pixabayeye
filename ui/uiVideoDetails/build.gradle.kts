plugins {
    id("composeMultiplatformKspConvention")
    id("testOptionsConvention")
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.video.details"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composemediaplayer)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidUnitTest.dependencies {
            implementation(libs.androidcontextprovider)
            implementation(projects.core.coreStubResources)
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
