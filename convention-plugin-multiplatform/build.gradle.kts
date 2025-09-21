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

    // Somewhat hacky way to access libs.version.toml in convention plugins.
    // IntelliJ can mark this code red, but it actually compiles.
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
