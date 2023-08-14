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

allprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(from = "$rootDir/ktlint.gradle.kts")
}

koverReport {
    verify {
        rule {
            minBound(95)
            maxBound(98)
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
    koverReport {
        defaults {
            mergeWith("debug")
        }
    }
    dependencies { kover(project(path)) }
}
