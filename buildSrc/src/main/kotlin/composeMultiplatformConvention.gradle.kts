val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("multiplatform")
}

kotlin {

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.findVersion("build-jvmTarget").get().requiredVersion
            }
        }
    }

    jvm("desktop")

    js {
        browser()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
            }
        }

        val desktopMain by getting {
            dependencies {
            }
        }

        val jsMain by getting {
            dependencies {
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = libs.findVersion("build-compileSdk").get().requiredVersion.toInt()
    defaultConfig.minSdk = libs.findVersion("build-minSdk").get().requiredVersion.toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(
            libs.findVersion("build-javaVersion").get().requiredVersion,
        )
        targetCompatibility = JavaVersion.valueOf(
            libs.findVersion("build-javaVersion").get().requiredVersion,
        )
    }
    packaging.resources.excludes.add("META-INF/**")
}
