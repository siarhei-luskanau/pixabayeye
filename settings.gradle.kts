rootProject.name = "PixabayEye"
include(
    ":composeApp",
    ":core:coreCommon",
    ":core:coreNetwork",
    ":core:corePref",
    ":navigation",
    ":ui:uiCommon",
    ":ui:uiDetails",
    ":ui:uiLogin",
    ":ui:uiSearch",
    ":ui:uiSplash"
)

pluginManagement {
    includeBuild("convention-plugin-multiplatform")
    includeBuild("convention-plugin-test-option")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
