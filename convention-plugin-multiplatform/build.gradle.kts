plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.tools.gradle)
    implementation(libs.jetbrains.compose.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.jetbrains.compose.compiler.plugin)

    // Somewhat hacky way to access libs.version.toml in convention plugins.
    // IntelliJ can mark this code red, but it actually compiles.
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
