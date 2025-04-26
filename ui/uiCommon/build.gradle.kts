import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.buildConfig)
}

kotlin {
    sourceSets {
        androidUnitTest.dependencies {
            implementation(projects.ui.uiScreenshotTest)
        }
    }
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.common"
    testOptions.configureTestOptions()
}

compose.resources {
    publicResClass = true
    packageOfResClass = "siarhei.luskanau.pixabayeye.ui.common.resources"
    generateResClass = always
}

// Directory for reference images
roborazzi.outputDir.set(rootProject.file("screenshots"))

buildConfig {
    packageName(android.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val isDebugScreenEnabled = isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }
    buildConfigField("Boolean", "IS_DEBUG_SCREEN_ENABLED", "$isDebugScreenEnabled")
}
