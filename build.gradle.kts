println("gradle.startParameter.taskNames: ${gradle.startParameter.taskNames}")
System.getProperties().forEach { key, value -> println("System.getProperties(): $key=$value") }
System.getenv().forEach { (key, value) -> println("System.getenv(): $key=$value") }

plugins {
    alias(libs.plugins.buildConfig).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.serialization).apply(false)
}

apply(from = "$rootDir/ci.gradle.kts")
allprojects { apply(from = "$rootDir/ktlint.gradle") }

subprojects {
    if (listOf(
            ":core",
            ":ui"
        ).contains(this.path).not()
    ) {
        apply(plugin = "org.jetbrains.kotlinx.kover")
        dependencies { kover(project(path)) }
    }
}

koverReport {
    verify {
        rule {
            minBound(95)
            maxBound(98)
        }
    }
}
