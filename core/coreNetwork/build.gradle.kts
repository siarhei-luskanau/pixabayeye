plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.kotlinx.serialization)
    id("testOptionsConvention")
    alias(libs.plugins.google.ksp)
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
            implementation(libs.ktor.client.okhttp)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        iosArm64Main {
            dependsOn(iosMain.get())
        }
        iosX64Main {
            dependsOn(iosMain.get())
        }
        iosSimulatorArm64Main {
            dependsOn(iosMain.get())
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

dependencies {
    ksp(libs.koin.ksp.compiler)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
