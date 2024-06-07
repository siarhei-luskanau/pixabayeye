plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.jetbrains.compose.compiler.plugin)
    implementation(libs.jetbrains.compose.plugin)
    implementation(libs.kotlin.gradle.plugin)
}
