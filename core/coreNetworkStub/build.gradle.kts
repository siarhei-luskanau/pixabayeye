plugins {
    id("composeMultiplatformConvention")
}

android {
    namespace = "siarhei.luskanau.pixabayeye.core.network.stub"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.coreNetworkApi)
            implementation(projects.core.coreStubResources)
        }
    }
}
