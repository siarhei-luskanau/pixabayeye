import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary {
        withDeviceTestBuilder {
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            animationsDisabled = true
            managedDevices.localDevices.create("managedVirtualDevice") {
                device = "Pixel 2"
                apiLevel = 35
            }
        }
    }

    sourceSets {
        getByName("androidHostTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.androidx.uitest.junit4)
                implementation(libs.androidx.uitest.testManifest)
            }
        }
    }
}
