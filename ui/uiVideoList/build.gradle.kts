plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.video.list"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.paging.compose)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        getByName("androidHostTest") {
            dependencies {
                implementation(projects.core.coreStubResources)
                implementation(projects.ui.uiScreenshotTest)
            }
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
