import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("composeMultiplatformConvention")
    id("io.github.takahirom.roborazzi")
}

kotlin {
    sourceSets {
        getByName("androidHostTest") {
            dependencies {
                implementation(libs.composable.preview.scanner.android)
                implementation(libs.junit)
                implementation(libs.robolectric)
                implementation(libs.roborazzi)
                implementation(libs.roborazzi.compose)
                implementation(libs.roborazzi.compose.preview.scanner.support)
            }
        }

        jvmTest.dependencies {
            implementation(libs.roborazzi.compose.desktop)
        }

        iosTest.dependencies {
            implementation(libs.roborazzi.compose.ios)
        }
    }
}

roborazzi {
    @OptIn(ExperimentalRoborazziApi::class)
    generateComposePreviewRobolectricTests {
        enable = true
        robolectricConfig =
            mapOf(
                "sdk" to "[36]",
                "qualifiers" to "RobolectricDeviceQualifiers.SmallPhone"
            )
        includePrivatePreviews = true
    }

    // Directory for reference images
    outputDir.set(file("src/screenshots"))
}
