plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.javax.json.api)
    implementation(libs.javax.json.glassfish)
}
