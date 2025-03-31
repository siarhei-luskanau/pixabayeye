import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    id("testOptionsConvention")
    alias(libs.plugins.buildConfig)
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

buildConfig {
    packageName(android.namespace.orEmpty())
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val isDebugScreenEnabled = isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }
    buildConfigField("Boolean", "IS_DEBUG_SCREEN_ENABLED", "$isDebugScreenEnabled")
}
