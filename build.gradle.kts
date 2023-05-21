plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.serialization).apply(false)
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
