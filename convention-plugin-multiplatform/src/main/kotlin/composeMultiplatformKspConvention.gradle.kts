val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("composeMultiplatformConvention")
    id("com.google.devtools.ksp")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.findLibrary("koin-annotations").get())
        }
    }
}

dependencies {
    // KSP Tasks
    add("kspAndroid", libs.findLibrary("koin-ksp-compiler").get())
    add("kspCommonMainMetadata", libs.findLibrary("koin-ksp-compiler").get())
    add("kspIosArm64", libs.findLibrary("koin-ksp-compiler").get())
    add("kspIosSimulatorArm64", libs.findLibrary("koin-ksp-compiler").get())
    add("kspIosX64", libs.findLibrary("koin-ksp-compiler").get())
    add("kspJs", libs.findLibrary("koin-ksp-compiler").get())
    add("kspJvm", libs.findLibrary("koin-ksp-compiler").get())
    add("kspWasmJs", libs.findLibrary("koin-ksp-compiler").get())
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
