package app

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.test.Test
import kotlin.test.assertEquals

class AppAndroidTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(
            expected = "siarhei.luskanau.compose.multiplatform.pixabayeye.androidApp",
            actual = appContext.packageName,
        )
    }

    @Test
    fun testActivity() {
        val scenario = ActivityScenario.launch(AppActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }
}
