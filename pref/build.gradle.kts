import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    composeMultiplatformConvention
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
}

android.namespace = "siarhei.luskanau.pixabayeye.pref"

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.androidx.datastore.core.okio)
            }
        }
        val androidMain by getting {
            dependsOn(desktopMain)
        }
        val iosMain by getting {
            dependsOn(desktopMain)
        }

        val jsMain by getting {
            dependencies {
            }
        }
    }
}

buildConfig {
    buildConfigField(
        "String",
        "PIXABAY_API_KEY",
        "\"${gradleLocalProperties(rootDir).getProperty("PIXABAY_API_KEY")}\"",
    )
}
