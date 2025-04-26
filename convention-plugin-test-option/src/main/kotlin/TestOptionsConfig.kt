import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestOptions
import org.gradle.kotlin.dsl.create

fun TestOptions.configureTestOptions() {
    unitTests {
        isIncludeAndroidResources = true
        all { test: org.gradle.api.tasks.testing.Test ->
            test.testLogging {
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                events = org.gradle.api.tasks.testing.logging.TestLogEvent.values().toSet()
            }
        }
    }
    animationsDisabled = true
    emulatorSnapshots {
        enableForTestFailures = false
    }
    managedDevices.allDevices.create<ManagedVirtualDevice>("managedVirtualDevice") {
        device = "Pixel 2"
        apiLevel = 33
        val systemImageConfig: Pair<String?, Boolean?> = when (apiLevel) {
            33, 34 -> "aosp-atd" to true
            else -> null to null
        }
        systemImageConfig.first?.also { systemImageSource = it }
        systemImageConfig.second?.also { require64Bit = it }
    }
}
