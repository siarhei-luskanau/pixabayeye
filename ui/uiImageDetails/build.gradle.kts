plugins {
    id("composeMultiplatformKspConvention")
    id("testOptionsConvention")
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.image.details"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidUnitTest.dependencies {
            implementation(projects.core.coreStubResources)
            implementation(projects.ui.uiScreenshotTest)
        }
        androidMain.dependencies {
            implementation(libs.zoomable)
        }
        iosMain.dependencies {
            implementation(libs.zoomable)
        }
        jvmMain.dependencies {
            implementation(libs.zoomable)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
