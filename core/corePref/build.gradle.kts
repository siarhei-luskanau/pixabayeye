import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("composeMultiplatformConvention")
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    id("testOptionsConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.pref"
    testOptions.configureTestOptions()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }

        jvmMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
        }

        androidMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
        }

        iosMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

buildConfig {
    packageName("siarhei.luskanau.pixabayeye.core.pref")
    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }
    val apiKey =
        gradleLocalProperties(rootDir, providers).getProperty("PIXABAY_API_KEY")
            ?: System.getenv("PIXABAY_API_KEY")
    buildConfigField("String", "PIXABAY_API_KEY", "\"${apiKey.orEmpty()}\"")
}
