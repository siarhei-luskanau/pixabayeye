plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.cocoapods).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.multiplatform).apply(false)
}

apply(from = "$rootDir/ci.gradle.kts")

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(from = "$rootDir/ktlint.gradle.kts")
}

dependencies {
    subprojects.map { it.path }.forEach {
        kover(project(it))
    }
}
