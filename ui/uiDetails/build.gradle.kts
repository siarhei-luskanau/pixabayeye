plugins {
    id("composeMultiplatformKspConvention")
    id("testOptionsConvention")
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.details"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:coreNetwork"))
            implementation(project(":core:corePref"))
            implementation(project(":ui:uiCommon"))
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
