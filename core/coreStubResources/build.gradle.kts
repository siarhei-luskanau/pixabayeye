plugins {
    id("composeMultiplatformConvention")
}

kotlin.androidLibrary.namespace = "siarhei.luskanau.pixabayeye.core.stub.resources"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreNetworkApi)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "${kotlin.androidLibrary.namespace}.resources"
    generateResClass = always
}
