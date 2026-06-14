import com.github.takahirom.roborazzi.ExperimentalRoborazziApi

plugins {
    id("composeMultiplatformConvention")
    id("roborazziConvention")
}

kotlin {
    android.namespace = "siarhei.luskanau.pixabayeye.ui.video.details"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composemediaplayer)
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

@OptIn(ExperimentalRoborazziApi::class)
roborazzi.generateComposePreviewRobolectricTests.packages = listOfNotNull(kotlin.android.namespace)
