plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.image.list"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.lazyPaginationCompose)
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
