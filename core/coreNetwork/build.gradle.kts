plugins {
    id("composeMultiplatformKspConvention")
    alias(libs.plugins.kotlinx.serialization)
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.network"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:coreCommon"))
            implementation(project(":core:corePref"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        jvmMain.dependencies {
            implementation(libs.inspektify.ktor3)
            implementation(libs.ktor.client.okhttp)
        }

        androidMain.dependencies {
            implementation(libs.inspektify.ktor3)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}
