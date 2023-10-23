plugins {
    composeMultiplatformConvention
    alias(libs.plugins.kotlinx.serialization)
}

android.namespace = "siarhei.luskanau.pixabayeye.core.network"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:coreCommon"))
                implementation(project(":core:corePref"))
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.core)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        val androidMain by getting {
            dependsOn(desktopMain)
        }

        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}
