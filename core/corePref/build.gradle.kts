import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    composeMultiplatformConvention
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
}

android.namespace = "siarhei.luskanau.pixabayeye.core.pref"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }

        jvmMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
        }

        androidMain {
            dependsOn(jvmMain.get())
        }

        iosMain {
            dependsOn(jvmMain.get())
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
        }
    }
}

buildConfig {
    packageName("siarhei.luskanau.pixabayeye.core.pref")
    useKotlinOutput { topLevelConstants = true }
    val apiKey =
        gradleLocalProperties(rootDir).getProperty("PIXABAY_API_KEY")
            ?: System.getenv("PIXABAY_API_KEY")
    buildConfigField("String", "PIXABAY_API_KEY", "\"${apiKey.orEmpty()}\"")
}
