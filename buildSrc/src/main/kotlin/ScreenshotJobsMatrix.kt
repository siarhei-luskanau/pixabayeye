import groovy.json.JsonOutput
import org.gradle.api.Project

fun getScreenshotMatrixJson(
    rootProject: Project,
    roborazziTask: String,
    includeMacOS: Boolean
): String {
    val modules =
        rootProject.subprojects
            .filter { subproject ->
                subproject.file("build.gradle.kts").let { file ->
                    file.exists() && file.readText().contains("id(\"roborazziConvention\")")
                }
            }.sortedBy { it.path }
    val matrix =
        modules.flatMap { subproject ->
            val iosGradleTask = "${subproject.path}:${roborazziTask}IosSimulatorArm64"
            val jvmAndroidGradleTasks =
                "${subproject.path}:${roborazziTask}Jvm " +
                    "${subproject.path}:${roborazziTask}AndroidHostTest"
            buildList {
                if (includeMacOS) {
                    add(
                        mapOf(
                            "gradle_tasks" to iosGradleTask,
                            "runner" to "macOS-26",
                            "module_path" to subproject.path
                        )
                    )
                }
                add(
                    mapOf(
                        "gradle_tasks" to jvmAndroidGradleTasks,
                        "runner" to "ubuntu-latest",
                        "module_path" to subproject.path
                    )
                )
            }
        }
    return JsonOutput.prettyPrint(JsonOutput.toJson(matrix))
}
