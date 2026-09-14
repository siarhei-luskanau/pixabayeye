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

tasks.withType<Test>().configureEach {
    if (name.contains(other = "AndroidHostTest", ignoreCase = true)) {
        // Robolectric reflectively pokes JDK internals (e.g. jdk.internal.access.SharedSecrets
        // for ApplicationSharedMemory on SDK 37+); modern JDKs (17+) hide those by default.
        jvmArgs(
            "--add-opens=java.base/java.lang=ALL-UNNAMED",
            "--add-opens=java.base/java.util=ALL-UNNAMED",
            "--add-opens=java.base/java.io=ALL-UNNAMED",
            "--add-opens=java.base/java.net=ALL-UNNAMED",
            "--add-opens=java.base/java.security=ALL-UNNAMED",
            "--add-opens=java.base/java.text=ALL-UNNAMED",
            "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
            "--add-opens=java.base/jdk.internal.access=ALL-UNNAMED",
            "--add-opens=java.base/jdk.internal.util.random=ALL-UNNAMED",
            "--add-opens=java.desktop/java.awt.font=ALL-UNNAMED"
        )
    }
}

roborazzi {
    @OptIn(ExperimentalRoborazziApi::class)
    generateComposePreviewRobolectricTests {
        enable = true
        robolectricConfig =
            mapOf(
                "sdk" to "[37]",
                "qualifiers" to "RobolectricDeviceQualifiers.SmallPhone"
            )
        includePrivatePreviews = true
    }

    // Directory for reference images
    outputDir.set(file("src/screenshots"))
}
