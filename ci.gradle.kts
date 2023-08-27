import org.apache.tools.ant.taskdefs.condition.Os
import java.util.Properties

val CI_GRADLE = "CI_GRADLE"

tasks.register("ciLint") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "ktlintCheck",
            "detekt",
            "lint",
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
            "koverVerify",
        )
    }
}

tasks.register("ciAndroid") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "assembleDebug",
        )
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
        gradlew(
            "managedVirtualDeviceCheck",
            "-Pandroid.testoptions.manageddevices.emulator.gpu=swiftshader_indirect",
        )
        gradlew("cleanManagedDevices")
    }
}

tasks.register("ciDesktop") {
    group = CI_GRADLE
    doLast {
        gradlew(":composeApp:desktopJar")
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
            gradlew("podInstall")
            runExec(
                listOf(
                    "xcodebuild",
                    "-workspace",
                    "${rootDir.path}/iosApp/iosApp.xcworkspace",
                    "-scheme",
                    "iosApp",
                    "-configuration",
                    "Debug",
                    "OBJROOT=${rootDir.path}/build/ios",
                    "SYMROOT=${rootDir.path}/build/ios",
                    "-sdk",
                    "iphonesimulator",
                    "-allowProvisioningDeviceRegistration",
                    "-allowProvisioningUpdates",
                ),
            )
        }
    }
}

tasks.register("devAll") {
    group = CI_GRADLE
    doLast {
        gradlew(
            "clean",
            "ktlintFormat",
            "ciLint",
            "ciUnitTest",
            "ciAndroid",
            "ciDesktop",
            "ciBrowser",
            "ciIos",
            "jsBrowserProductionWebpack",
            "ciAndroidEmulator",
            "managedVirtualDeviceCheck",
        )
    }
}

fun runExec(commands: List<String>) =
    exec {
        commandLine = commands
        println("commandLine: ${this.commandLine.joinToString(separator = " ")}")
    }.apply { println("ExecResult: $this") }

fun gradlew(
    vararg tasks: String,
    addToSystemProperties: Map<String, String>? = null,
) {
    exec {
        executable = File(
            project.rootDir,
            if (Os.isFamily(Os.FAMILY_WINDOWS)) "gradlew.bat" else "gradlew",
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
