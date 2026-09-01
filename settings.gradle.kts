rootProject.name = "PixabayEye"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":app:androidApp",
    ":app:desktopApp",
    ":app:webApp",
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
    ":ui:uiMediaDetails",
    ":ui:uiMediaList"
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
