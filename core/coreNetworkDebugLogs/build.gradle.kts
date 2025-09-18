plugins {
    id("composeMultiplatformConvention")
}

kotlin {
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

android {
    namespace = "siarhei.luskanau.pixabayeye.core.network.debug.logs"
}
