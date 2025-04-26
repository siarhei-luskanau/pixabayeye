package siarhei.luskanau.pixabayeye.ui.screenshot.test

import android.content.ContentProvider
import androidx.compose.ui.test.SemanticsNodeInteraction
import com.dropbox.differ.SimpleImageComparator
import com.github.takahirom.roborazzi.Dump
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.ThresholdValidator
import com.github.takahirom.roborazzi.captureRoboImage
import org.robolectric.Robolectric

open class BaseScreenshotTest(private val group: String) {

    protected fun SemanticsNodeInteraction.captureScreenshot(name: String) {
        this.captureRoboImage(
            filePath = "${group}_$name.png",
            roborazziOptions = RoborazziOptions(compareOptions = getCompareOptions())
        )
    }

    protected fun SemanticsNodeInteraction.captureScreenshotDump(name: String) {
        this.captureRoboImage(
            filePath = "${group}_$name.png",
            roborazziOptions = RoborazziOptions(
                compareOptions = getCompareOptions(),
                captureType = RoborazziOptions.CaptureType.Dump()
            )
        )
    }

    private fun getCompareOptions() = CompareOptions(
        resultValidator = ThresholdValidator(0.01F), // For 1% accepted difference
        imageComparator = SimpleImageComparator(
            maxDistance = 0.007F, // 0.001F is default value from Differ
            vShift = 2, // Increasing the shift can help resolve antialiasing issues
            hShift = 2 // Increasing the shift can help resolve antialiasing issues
        )
    )

    // Configures Compose's AndroidContextProvider to access resources in tests.
    // See https://youtrack.jetbrains.com/issue/CMP-6612
    protected fun setupAndroidContextProvider() {
        findAndroidContextProvider()?.also { Robolectric.setupContentProvider(it) }
    }

    private fun findAndroidContextProvider(): Class<ContentProvider>? {
        val providerClassName = "org.jetbrains.compose.resources.AndroidContextProvider"
        return try {
            @Suppress("UNCHECKED_CAST")
            Class.forName(providerClassName) as Class<ContentProvider>
        } catch (_: ClassNotFoundException) {
            // Tests that don't depend on Compose will not have the provider class in classpath and will get
            // ClassNotFoundException. Skip configuring the provider for them.
            null
        }
    }
}
