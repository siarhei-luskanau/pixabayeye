rootProject.name = "PixabayEye"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":composeApp",
    ":core:coreCommon",
    ":core:coreNetwork",
    ":core:corePref",
    ":navigation",
    ":ui:uiCommon",
    ":ui:uiDetails",
    ":ui:uiSearch"
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
