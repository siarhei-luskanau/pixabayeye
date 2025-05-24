plugins {
    id("composeMultiplatformConvention")
}

kotlin {
    androidLibrary.namespace = "siarhei.luskanau.pixabayeye.core.network.debug.logs"
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.logging)
        }
        jvmMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
        androidMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
        iosMain.dependencies {
            implementation(libs.inspektify.ktor3)
        }
    }
}
