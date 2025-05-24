plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.ui.debug"
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
