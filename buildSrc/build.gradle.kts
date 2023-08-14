plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(buildSrcLibs.android.gradle.plugin)
    implementation(buildSrcLibs.jetbrains.compose.plugin)
    implementation(buildSrcLibs.kotlin.gradle.plugin)
}
