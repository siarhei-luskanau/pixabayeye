import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.api.dsl.TestOptions
import org.gradle.kotlin.dsl.create

fun TestOptions.configureTestOptions() {
    unitTests {
        all { test: org.gradle.api.tasks.testing.Test ->
            test.testLogging.events = setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
            )
        }
    }
    animationsDisabled = true
    emulatorSnapshots {
        enableForTestFailures = false
    }
    managedDevices.devices.create<ManagedVirtualDevice>("managedVirtualDevice") {
        device = "Pixel 2"
        apiLevel = 33
    }
}
