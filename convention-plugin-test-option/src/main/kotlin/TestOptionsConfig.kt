import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestOptions
import org.gradle.kotlin.dsl.create

fun TestOptions.configureTestOptions() {
    unitTests {
        all { test: org.gradle.api.tasks.testing.Test ->
            test.testLogging {
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
                events = setOf(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
                )
            }
        }
    }
    animationsDisabled = true
    emulatorSnapshots {
        enableForTestFailures = false
    }
    managedDevices.devices.create<ManagedVirtualDevice>("managedVirtualDevice") {
        device = "Pixel 2"
        apiLevel = 33
        val systemImageConfig: Pair<String?, Boolean?> = when (apiLevel) {
            34 -> "aosp-atd" to true
            else -> null to null
        }
        systemImageConfig.first?.also { systemImageSource = it }
        systemImageConfig.second?.also { require64Bit = it }
    }
}
