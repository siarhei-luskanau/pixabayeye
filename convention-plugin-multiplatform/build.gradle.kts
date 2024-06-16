plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.jetbrains.compose.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    runtimeOnly(libs.android.gradle.plugin)
    runtimeOnly(libs.jetbrains.compose.compiler.plugin)
    runtimeOnly(libs.jetbrains.compose.plugin)
    runtimeOnly(libs.kotlin.gradle.plugin)
}
