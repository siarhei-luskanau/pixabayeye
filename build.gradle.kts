@file:Suppress("PropertyName")

import groovy.json.JsonSlurper
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
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.ksp).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
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

val CI_GRADLE = "CI_GRADLE"

tasks.register("ciLint") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew(
            "ktlintCheck",
            "detekt",
            "lint"
        )
    }
}

tasks.register("ciUpdateScreenshot") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew("ktlintFormat")
    }
}

tasks.register("ciAndroid") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew("assembleDebug", "assembleRelease")
    }
}

tasks.register("ciAndroidEmulator") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
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
        injected.gradlew(*tasks.toTypedArray())
        injected.gradlew("cleanManagedDevices")
    }
}

tasks.register("ciDesktop") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew(":composeApp:jvmJar")
    }
}

tasks.register("ciJsBrowser") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew(":composeApp:jsMainClasses", ":composeApp:jsBrowserDistribution")
    }
}

tasks.register("ciWasmJsBrowser") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew(":composeApp:wasmJsMainClasses", ":composeApp:wasmJsBrowserDistribution")
    }
}

tasks.register("ciPngCheck") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        if (Os.isFamily(Os.FAMILY_MAC)) {
            injected.runExec(listOf("brew", "install", "pngcheck"))
        } else if (Os.isFamily(Os.FAMILY_UNIX)) {
            injected.runExec(listOf("sudo", "apt", "install", "pngcheck"))
        }
        File(injected.projectLayout.projectDirectory.asFile, "screenshots").listFiles().orEmpty()
            .filter { it.name.endsWith(".png", ignoreCase = true) }
            .forEach { injected.runExec(listOf("pngcheck", "-q", it.path)) }
    }
}

tasks.register("ciIos") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        if (Os.isFamily(Os.FAMILY_MAC)) {
            injected.runExec(listOf("brew", "install", "kdoctor"))
            injected.runExec(listOf("kdoctor"))
            val devicesJson = injected.runExec(
                listOf(
                    "xcrun",
                    "simctl",
                    "list",
                    "devices",
                    "available",
                    "-j"
                )
            )

            @Suppress("UNCHECKED_CAST")
            val devicesList = (JsonSlurper().parseText(devicesJson) as Map<String, *>)
                .let { it["devices"] as Map<String, *> }
                .let { devicesMap ->
                    devicesMap.keys
                        .filter { it.startsWith("com.apple.CoreSimulator.SimRuntime.iOS") }
                        .map { devicesMap[it] as List<*> }
                }
                .map { jsonArray -> jsonArray.map { it as Map<String, *> } }
                .flatten()
                .filter { it["isAvailable"] as Boolean }
                .filter {
                    listOf("iphone 1").any { device ->
                        (it["name"] as String).contains(device, true)
                    }
                }
            println("Devices:${devicesList.joinToString { "\n" + it["udid"] + ": " + it["name"] }}")
            val device = devicesList.firstOrNull()
            println("Selected:\n${device?.get("udid")}: ${device?.get("name")}")
            val rootDirPath = injected.projectLayout.projectDirectory.asFile.path
            injected.runExec(
                listOf(
                    "xcodebuild",
                    "-project",
                    "$rootDirPath/iosApp/iosApp.xcodeproj",
                    "-scheme",
                    "iosApp",
                    "-configuration",
                    "Debug",
                    "OBJROOT=$rootDirPath/build/ios",
                    "SYMROOT=$rootDirPath/build/ios",
                    "-destination",
                    "id=${device?.get("udid")}",
                    "-allowProvisioningDeviceRegistration",
                    "-allowProvisioningUpdates"
                )
            )
        }
    }
}

tasks.register("ciSdkManagerLicenses") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        val sdkDirPath = injected.getAndroidSdkPath()
        injected.getSdkManagerFile()?.let { sdkManagerFile ->
            val yesInputStream = object : InputStream() {
                private val yesString = "y\n"
                private var counter = 0
                override fun read(): Int = yesString[counter % 2].also { counter++ }.code
            }
            injected.execOperations.exec {
                commandLine =
                    listOf(sdkManagerFile.absolutePath, "--list", "--sdk_root=$sdkDirPath")
                println("exec: ${this.commandLine.joinToString(separator = " ")}")
            }.apply { println("ExecResult: ${this.exitValue}") }
            injected.execOperations.exec {
                commandLine =
                    listOf(sdkManagerFile.absolutePath, "--licenses", "--sdk_root=$sdkDirPath")
                standardInput = yesInputStream
                println("exec: ${this.commandLine.joinToString(separator = " ")}")
            }.apply { println("ExecResult: ${this.exitValue}") }
        }
    }
}

tasks.register("devAll") {
    group = CI_GRADLE
    val injected = project.objects.newInstance<Injected>()
    doLast {
        injected.gradlew(
            "clean",
            "ktlintFormat"
        )
        injected.gradlew(
            "ciLint",
            "ciUnitTest",
            "ciAndroid",
            "ciDesktop"
        )
        injected.gradlew("ciIos")
        injected.gradlew("ciBrowser")
        injected.gradlew("ciSdkManagerLicenses")
        injected.gradlew("ciAndroidEmulator")
    }
}

abstract class Injected {

    @get:Inject abstract val fs: FileSystemOperations

    @get:Inject abstract val execOperations: ExecOperations

    @get:Inject abstract val projectLayout: ProjectLayout

    fun gradlew(vararg tasks: String, addToSystemProperties: Map<String, String>? = null) {
        execOperations.exec {
            commandLine = mutableListOf<String>().also { mutableArgs ->
                mutableArgs.add(
                    projectLayout.projectDirectory.file(
                        if (Os.isFamily(Os.FAMILY_WINDOWS)) "gradlew.bat" else "gradlew"
                    ).asFile.path
                )
                mutableArgs.addAll(tasks)
                addToSystemProperties?.toList()?.map { "-D${it.first}=${it.second}" }?.let {
                    mutableArgs.addAll(it)
                }
                mutableArgs.add("--stacktrace")
            }
            val sdkDirPath = Properties().apply {
                val propertiesFile = projectLayout.projectDirectory.file("local.properties").asFile
                if (propertiesFile.exists()) {
                    load(propertiesFile.inputStream())
                }
            }.getProperty("sdk.dir")
            if (sdkDirPath != null) {
                val platformToolsDir = "$sdkDirPath${File.separator}platform-tools"
                val pathEnvironment = System.getenv("PATH").orEmpty()
                if (!pathEnvironment.contains(platformToolsDir)) {
                    environment = environment.toMutableMap().apply {
                        put("PATH", "$platformToolsDir:$pathEnvironment")
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
                    put("ANDROID_HOME", sdkDirPath)
                }
            }
            println("commandLine: ${this.commandLine}")
        }.apply { println("ExecResult: ${this.exitValue}") }
    }

    fun runExec(commands: List<String>): String = object : ByteArrayOutputStream() {
        override fun write(p0: ByteArray, p1: Int, p2: Int) {
            print(String(p0, p1, p2))
            super.write(p0, p1, p2)
        }
    }.let { resultOutputStream ->
        execOperations.exec {
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
        String(resultOutputStream.toByteArray())
    }

    fun getAndroidSdkPath(): String? = Properties().apply {
        val propertiesFile = File(projectLayout.projectDirectory.asFile, "local.properties")
        if (propertiesFile.exists()) {
            load(propertiesFile.inputStream())
        }
    }.getProperty("sdk.dir").let { propertiesSdkDirPath ->
        (propertiesSdkDirPath ?: System.getenv("ANDROID_HOME"))
    }

    fun getSdkManagerFile(): File? = getAndroidSdkPath()?.let { sdkDirPath ->
        println("sdkDirPath: $sdkDirPath")
        val files = File(sdkDirPath).walk().filter { file ->
            file.path.contains("cmdline-tools") && file.path.endsWith("sdkmanager")
        }
        files.forEach { println("walk: ${it.absolutePath}") }
        val sdkmanagerFile = files.firstOrNull()
        println("sdkmanagerFile: $sdkmanagerFile")
        sdkmanagerFile
    }
}
