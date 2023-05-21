plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(buildSrcLibs.android.gradle.plugin)
    implementation(buildSrcLibs.jetbrains.compose.plugin)
    implementation(buildSrcLibs.kotlin.gradle.plugin)
}
