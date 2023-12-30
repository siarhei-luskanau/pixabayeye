println("gradle.startParameter.taskNames: ${gradle.startParameter.taskNames}")
System.getProperties().forEach { key, value -> println("System.getProperties(): $key=$value") }
System.getenv().forEach { (key, value) -> println("System.getenv(): $key=$value") }

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.multiplatform).apply(false)
}

apply(from = "$rootDir/ci.gradle.kts")
allprojects {
    apply(from = "$rootDir/ktlint.gradle")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        parallel = true
        ignoreFailures = false
    }
}

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
