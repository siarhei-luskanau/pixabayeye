import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.multiplatform)
    id("testOptionsConvention")
}

kotlin {
    jvmToolchain(libs.versions.build.jvmTarget.get().toInt())

    androidTarget {
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(projects.core.coreCommon)
            implementation(projects.core.coreNetwork)
            implementation(projects.core.corePref)
            implementation(projects.navigation)
            implementation(projects.ui.uiCommon)
            implementation(projects.ui.uiDetails)
            implementation(projects.ui.uiSearch)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.datastore.core.okio)
        }

        androidInstrumentedTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.androidx.test.core.ktx)
            implementation(libs.androidx.test.runner)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.androidx.datastore.core.okio)
        }

        iosMain.dependencies {
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "siarhei.luskanau.compose.multiplatform.pixabayeye"
    compileSdk = libs.versions.build.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.build.android.minSdk.get().toInt()
        targetSdk = libs.versions.build.android.targetSdk.get().toInt()
        applicationId = "siarhei.luskanau.compose.multiplatform.pixabayeye.androidApp"
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.build.javaVersion.get())
    }
    buildFeatures.compose = true
    packaging.resources.excludes.add("META-INF/**")
    testOptions.configureTestOptions()
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "siarhei.luskanau.compose.multiplatform.pixabayeye.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    // https://developer.android.com/develop/ui/compose/testing#setup
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)

    // KSP Tasks
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
    add("kspIosX64", libs.koin.ksp.compiler)
    add("kspJs", libs.koin.ksp.compiler)
    add("kspJvm", libs.koin.ksp.compiler)
    debugImplementation(libs.leakcanary.android)
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
