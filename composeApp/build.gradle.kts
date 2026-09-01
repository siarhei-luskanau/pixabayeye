import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi

plugins {
    id("androidTestConvention")
    id("composeMultiplatformConvention")
    id("roborazziConvention")
}

kotlin {
    android.namespace = "siarhei.luskanau.pixabayeye.app"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.navigation)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiMediaDetails)
            implementation(projects.ui.uiMediaList)
            if (isDataStubEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.core.coreNetworkStub)
            } else {
                implementation(projects.core.coreNetworkKtor)
            }
            if (isDebugScreenEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.ui.uiDebug)
            } else {
                implementation(projects.ui.uiDebugEmpty)
            }
        }
    }
}

@OptIn(ExperimentalRoborazziApi::class)
roborazzi.generateComposePreviewRobolectricTests.packages = listOfNotNull(kotlin.android.namespace)

koinCompiler {
    // iosMain's Koin.get(ObjCClass) bridge for Swift interop resolves by dynamic
    // KClass<*> at runtime, which the full-graph compile-safety checker can't
    // statically verify (always reports a false-positive "Missing definition:
    // kotlin.Any"). The DI graph itself is still validated on the JVM/Android
    // compiles, where this checker stays enabled.
    compileSafety = false
}
