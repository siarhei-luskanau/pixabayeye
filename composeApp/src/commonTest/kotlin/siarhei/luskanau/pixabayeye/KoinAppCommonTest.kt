package siarhei.luskanau.pixabayeye

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KoinAppCommonTest {

    @Test
    fun simpleCheck() = runComposeUiTest {
        setContent { KoinApp() }
        onRoot().printToLog("StartTag")
        onNodeWithContentDescription("Back").performClick()
        val testInput = "test123abc"
        onNodeWithTag("search_text_field").apply {
            performTextInput(text = testInput)
            assertTextContains(value = testInput)
        }
        onNodeWithText("Videos").performClick()
        onNodeWithText(testInput).assertIsDisplayed()
    }
}
