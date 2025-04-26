rootProject.name = "PixabayEye"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":composeApp",
    ":core:coreCommon",
    ":core:coreNetwork",
    ":core:coreNetworkDebugEmpty",
    ":core:coreNetworkDebugLogs",
    ":core:corePref",
    ":navigation",
    ":ui:uiCommon",
    ":ui:uiDebug",
    ":ui:uiDebugEmpty",
    ":ui:uiDetails",
    ":ui:uiScreenshotTest",
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
