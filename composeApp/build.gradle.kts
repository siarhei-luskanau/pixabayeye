import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("androidTestConvention")
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.app"
    sourceSets {
        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(compose.ui)
            implementation(libs.koin.compose)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.navigation)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiImageDetails)
            implementation(projects.ui.uiImageList)
            implementation(projects.ui.uiVideoDetails)
            implementation(projects.ui.uiVideoList)
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

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(kotlin("test"))
            implementation(libs.jetbrains.navigation3.ui)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.datastore.core.okio)
        }

        androidInstrumentedTest.dependencies {
            implementation(kotlin("test"))
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.androidx.datastore.core.okio)
        }

        iosMain.dependencies {
        }

        webMain.dependencies {
        }
    }
}
