import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.buildConfig)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.common"
    sourceSets {
        androidUnitTest.dependencies {
            implementation(projects.core.coreStubResources)
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "${kotlin.androidLibrary.namespace}.resources"
    generateResClass = always
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))

buildConfig {
    packageName(kotlin.androidLibrary.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val isDebugScreenEnabled = isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }
    buildConfigField("Boolean", "IS_DEBUG_SCREEN_ENABLED", "$isDebugScreenEnabled")
}
