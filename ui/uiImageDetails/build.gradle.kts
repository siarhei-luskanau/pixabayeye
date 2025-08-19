plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.image.details"
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidUnitTest.dependencies {
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
