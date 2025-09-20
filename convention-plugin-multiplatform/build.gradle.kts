plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.tools.gradle)
    compileOnly(libs.jetbrains.compose.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    runtimeOnly(libs.android.tools.gradle)
    runtimeOnly(libs.jetbrains.compose.compiler.plugin)
    runtimeOnly(libs.jetbrains.compose.plugin)
    runtimeOnly(libs.kotlin.gradle.plugin)
}
