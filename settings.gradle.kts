rootProject.name = "PixabayEye"
include(
    ":composeApp",
    ":core",
    ":network",
    ":pref",
    ":ui",
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
