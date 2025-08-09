import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "siarhei.luskanau.pixabayeye.ui.debug"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
            if (isDataStubEnabled { gradleLocalProperties(rootDir, providers) }) {
                implementation(projects.core.coreNetworkStub)
            } else {
                implementation(projects.core.coreNetworkKtor)
            }
        }
        androidMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
        jvmMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
        iosMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
    }
}
