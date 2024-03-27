@file:Suppress("PropertyName")

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.Properties
import org.apache.tools.ant.taskdefs.condition.Os

println("gradle.startParameter.taskNames: ${gradle.startParameter.taskNames}")
System.getProperties().forEach { key, value -> println("System.getProperties(): $key=$value") }
System.getenv().forEach { (key, value) -> println("System.getenv(): $key=$value") }

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.multiplatform).apply(false)
}

allprojects {
    apply(from = "$rootDir/ktlint.gradle")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        parallel = true
        ignoreFailures = false
    }
}

subprojects {
    if (listOf(
            ":core",
            ":ui"
        ).contains(this.path).not()
    ) {
        apply(plugin = "org.jetbrains.kotlinx.kover")
        dependencies { kover(project(path)) }
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

val CI_GRADLE = "CI_GRADLE"

tasks.register("ciLint") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "ktlintCheck",
            "detekt",
            "lint"
        )
    }
}

tasks.register("ciUnitTest") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "clean",
            "koverXmlReportDebug",
            "koverXmlReport",
            "koverHtmlReportDebug",
            "koverHtmlReport",
            "koverVerifyDebug",
            "koverVerify"
        )
    }
}

tasks.register("ciAndroid") {
    group = CI_GRADLE
    doLast {
        gradlew("assembleDebug")
        copy {
            from(rootProject.subprojects.map { it.buildDir })
            include("**/*.apk")
            exclude("**/apk/androidTest/**")
            eachFile { path = name }
            includeEmptyDirs = false
            into("$buildDir/apk/")
        }
    }
}

tasks.register("ciAndroidEmulator") {
    group = CI_GRADLE
    doLast {
        val tasks = mutableListOf(
            "managedVirtualDeviceDebugAndroidTest",
            "--no-parallel",
            "--max-workers=1",
            "-Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect",
            "-Pandroid.experimental.testOptions.managedDevices.emulator.showKernelLogging=true"
        )
        if (!true.toString().equals(other = System.getenv("CI"), ignoreCase = true)) {
            tasks.add("--enable-display")
        }
        gradlew(*tasks.toTypedArray())
        gradlew("cleanManagedDevices")
    }
}

tasks.register("ciDesktop") {
    group = CI_GRADLE
    doLast {
        gradlew(":composeApp:jvmJar")
    }
}

tasks.register("ciBrowser") {
    group = CI_GRADLE
    doLast {
        gradlew(":composeApp:jsMainClasses")
    }
}

tasks.register("ciIos") {
    group = CI_GRADLE
    doLast {
        if (Os.isFamily(Os.FAMILY_MAC)) {
            runExec(listOf("brew", "install", "kdoctor"))
            runExec(listOf("kdoctor"))
            val devicesJson = runExec(
                listOf(
                    "xcrun",
                    "simctl",
                    "list",
                    "devices",
                    "available",
                    "-j"
                )
            )
            val devicesList = Gson().fromJson(devicesJson, XcodeDeviceResponse::class.java)
                ?.devices.orEmpty()
                .mapValues { entry ->
                    entry.value
                        .filter { it.isAvailable }
                        .filter {
                            listOf(
                                "iphone 15",
                                "iphone 14"
                            ).any { device -> it.name.contains(device, true) }
                        }
                }
                .values.flatten()
            println("Devices:$devicesList")
            val deviceId = devicesList.firstOrNull()?.udid
            runExec(
                listOf(
                    "xcodebuild",
                    "-project",
                    "${rootDir.path}/iosApp/iosApp.xcodeproj",
                    "-scheme",
                    "iosApp",
                    "-configuration",
                    "Debug",
                    "OBJROOT=${rootDir.path}/build/ios",
                    "SYMROOT=${rootDir.path}/build/ios",
                    "-destination",
                    "id=$deviceId",
                    "-allowProvisioningDeviceRegistration",
                    "-allowProvisioningUpdates"
                )
            )
        }
    }
}

tasks.register("ciSdkManagerLicenses") {
    group = CI_GRADLE
    doLast {
        val sdkDirPath = getAndroidSdkPath(rootDir = rootDir)
        getSdkmanagerFile(rootDir = rootDir)?.let { sdkmanagerFile ->
            val yesInputStream = object : InputStream() {
                private val yesString = "y\n"
                private var counter = 0
                override fun read(): Int = yesString[counter % 2].also { counter++ }.code
            }
            exec {
                executable = sdkmanagerFile.absolutePath
                args = listOf("--list", "--sdk_root=$sdkDirPath")
                println("exec: ${this.commandLine.joinToString(separator = " ")}")
            }.apply { println("ExecResult: $this") }
            exec {
                executable = sdkmanagerFile.absolutePath
                args = listOf("--licenses", "--sdk_root=$sdkDirPath")
                standardInput = yesInputStream
                println("exec: ${this.commandLine.joinToString(separator = " ")}")
            }.apply { println("ExecResult: $this") }
        }
    }
}

tasks.register("devAll") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "clean",
            "ktlintFormat"
        )
        gradlew(
            "ciLint",
            "ciUnitTest",
            "ciAndroid",
            "ciDesktop"
        )
        // gradlew("ciIos")
        gradlew("ciBrowser")
        gradlew("jsBrowserProductionWebpack")
        gradlew("ciSdkManagerLicenses")
        gradlew("ciAndroidEmulator")
    }
}

fun runExec(commands: List<String>): String = ByteArrayOutputStream().let { resultOutputStream ->
    exec {
        if (System.getenv("JAVA_HOME") == null) {
            System.getProperty("java.home")?.let { javaHome ->
                environment = environment.toMutableMap().apply {
                    put("JAVA_HOME", javaHome)
                }
            }
        }
        commandLine = commands
        standardOutput = resultOutputStream
        println("commandLine: ${this.commandLine.joinToString(separator = " ")}")
    }.apply { println("ExecResult: $this") }
    String(resultOutputStream.toByteArray()).trim().also { println(it) }
}

fun gradlew(vararg tasks: String, addToSystemProperties: Map<String, String>? = null) {
    exec {
        executable = File(
            project.rootDir,
            if (Os.isFamily(Os.FAMILY_WINDOWS)) "gradlew.bat" else "gradlew"
        )
            .also { it.setExecutable(true) }
            .absolutePath
        args = mutableListOf<String>().also { mutableArgs ->
            mutableArgs.addAll(tasks)
            addToSystemProperties?.toList()?.map { "-D${it.first}=${it.second}" }?.let {
                mutableArgs.addAll(it)
            }
            mutableArgs.add("--stacktrace")
        }
        val sdkDirPath = Properties().apply {
            val propertiesFile = File(rootDir, "local.properties")
            if (propertiesFile.exists()) {
                load(propertiesFile.inputStream())
            }
        }.getProperty("sdk.dir")
        if (sdkDirPath != null) {
            val platformToolsDir = "$sdkDirPath${java.io.File.separator}platform-tools"
            val pahtEnvironment = System.getenv("PATH").orEmpty()
            if (!pahtEnvironment.contains(platformToolsDir)) {
                environment = environment.toMutableMap().apply {
                    put("PATH", "$platformToolsDir:$pahtEnvironment")
                }
            }
        }
        if (System.getenv("JAVA_HOME") == null) {
            System.getProperty("java.home")?.let { javaHome ->
                environment = environment.toMutableMap().apply {
                    put("JAVA_HOME", javaHome)
                }
            }
        }
        if (System.getenv("ANDROID_HOME") == null) {
            environment = environment.toMutableMap().apply {
                put("ANDROID_HOME", "$sdkDirPath")
            }
        }
        println("commandLine: ${this.commandLine}")
    }.apply { println("ExecResult: $this") }
}

fun getAndroidSdkPath(rootDir: File): String? = Properties().apply {
    val propertiesFile = File(rootDir, "local.properties")
    if (propertiesFile.exists()) {
        load(propertiesFile.inputStream())
    }
}.getProperty("sdk.dir").let { propertiesSdkDirPath ->
    (propertiesSdkDirPath ?: System.getenv("ANDROID_HOME"))
}

fun getSdkmanagerFile(rootDir: File): File? = getAndroidSdkPath(
    rootDir = rootDir
)?.let { sdkDirPath ->
    println("sdkDirPath: $sdkDirPath")
    val files = File(sdkDirPath).walk().filter { file ->
        file.path.contains("cmdline-tools") && file.path.endsWith("sdkmanager")
    }
    files.forEach { println("walk: ${it.absolutePath}") }
    val sdkmanagerFile = files.firstOrNull()
    println("sdkmanagerFile: $sdkmanagerFile")
    sdkmanagerFile
}

private data class XcodeDeviceResponse(
    @SerializedName("devices") val devices: Map<String, List<XcodeDevice>>
)

private data class XcodeDevice(
    @SerializedName("udid") val udid: String,
    @SerializedName("state") val state: String,
    @SerializedName("name") val name: String,
    @SerializedName("isAvailable") val isAvailable: Boolean,
    @SerializedName("platformVersion") val platformVersion: String? = null
)
