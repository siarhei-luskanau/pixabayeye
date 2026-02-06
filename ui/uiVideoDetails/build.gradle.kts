plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.video.details"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composemediaplayer)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidHostTest.dependencies {
            implementation(libs.androidcontextprovider)
            implementation(projects.core.coreStubResources)
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
