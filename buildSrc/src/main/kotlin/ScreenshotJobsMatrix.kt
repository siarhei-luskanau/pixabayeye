import groovy.json.JsonOutput
import org.gradle.api.Project

fun getScreenshotMatrixJson(rootProject: Project, roborazziTask: String): String {
    val matrix = rootProject.subprojects
        .filter { subproject ->
            subproject.file("build.gradle.kts").let { file ->
                file.exists() && file.readText().contains("alias(libs.plugins.roborazzi)")
            }
        }
        .map { subproject ->
            mapOf("gradle_tasks" to "${subproject.path}:$roborazziTask")
        }
        .sortedBy { it["gradle_tasks"] }
    return JsonOutput.prettyPrint(JsonOutput.toJson(matrix))
}
