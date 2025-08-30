rootProject.name = "PixabayEye"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":composeApp",
    ":core:coreCommon",
    ":core:coreNetworkApi",
    ":core:coreNetworkDebugEmpty",
    ":core:coreNetworkDebugLogs",
    ":core:coreNetworkKtor",
    ":core:coreNetworkStub",
    ":core:corePref",
    ":core:coreStubResources",
    ":navigation",
    ":ui:uiCommon",
    ":ui:uiDebug",
    ":ui:uiDebugEmpty",
    ":ui:uiImageDetails",
    ":ui:uiImageList",
    ":ui:uiScreenshotTest",
    ":ui:uiVideoDetails",
    ":ui:uiVideoList"
)

pluginManagement {
    includeBuild("convention-plugin-multiplatform")
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
