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
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.ui.uiCommon)
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
