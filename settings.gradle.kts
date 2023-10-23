rootProject.name = "PixabayEye"
include(
    ":composeApp",
    ":core:coreCommon",
    ":core:coreNetwork",
    ":core:corePref",
    ":navigation",
    ":ui:uiDetails",
    ":ui:uiLogin",
    ":ui:uiSearch",
    ":ui:uiSplash"
)

pluginManagement {
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
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
