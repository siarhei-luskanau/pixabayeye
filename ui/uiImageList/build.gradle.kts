plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.roborazzi)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.image.list"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.paging.compose)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
        }
        androidHostTest.dependencies {
            implementation(projects.core.coreStubResources)
        }
        iosTest.dependencies {
            implementation(projects.core.coreStubResources)
        }
        jvmTest.dependencies {
            implementation(projects.core.coreStubResources)
        }
    }
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))
