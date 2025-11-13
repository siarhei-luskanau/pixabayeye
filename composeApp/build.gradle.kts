import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("androidTestConvention")
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.app"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.animation)
            implementation(libs.jetbrains.compose.animation.graphics)
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material.icons.extended)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.material3.adaptive.navigation.suite)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.runtime.saveable)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.compose.ui.tooling.preview)
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
            implementation(kotlin("test"))
            implementation(libs.jetbrains.compose.ui.test)
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
