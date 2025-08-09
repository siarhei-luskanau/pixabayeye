plugins {
    id("composeMultiplatformConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.stub.resources"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreNetworkApi)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "${android.namespace}.resources"
    generateResClass = always
}
