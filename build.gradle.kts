println("gradle.startParameter.taskNames: ${gradle.startParameter.taskNames}")
System.getProperties().forEach { key, value -> println("System.getProperties(): $key=$value") }
System.getenv().forEach { (key, value) -> println("System.getenv(): $key=$value") }

plugins {
    alias(libs.plugins.buildConfig).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.ktlint.jlleitschuh)
}

apply(from = "$rootDir/ci.gradle.kts")

subprojects {
    if (listOf(
            ":core",
            ":ui"
        ).contains(this.path).not()
    ) {
        apply(plugin = "org.jetbrains.kotlinx.kover")
        apply(plugin = "org.jlleitschuh.gradle.ktlint")
        dependencies { kover(project(path)) }
        ktlint {
            version.set("1.0.1")
            android.set(true)
            filter {
                exclude("**/generated/**")
            }
        }
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
