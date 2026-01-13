import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.jetbrains.compose.components.resources)
    implementation(libs.jetbrains.compose.ui)
    implementation(projects.composeApp)
    implementation(projects.ui.uiCommon)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "PixabayEye"
            packageVersion = "1.0.0"

            linux {}
            windows {}
            macOS {
                bundleID = "siarhei.luskanau.pixabayeye"
            }
        }
    }
}
