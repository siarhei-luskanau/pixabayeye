package siarhei.luskanau.pixabayeye

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KoinAppCommonTest {

    @Test
    fun simpleCheck() = runComposeUiTest {
        setContent { KoinApp() }
        onRoot().printToLog("StartTag")
        val testInput = "test123abc"
        onNodeWithTag("search_text_field").apply {
            performTextInput(text = testInput)
            waitForIdle()
            awaitIdle()
            assertTextContains(value = testInput)
        }
        onAllNodes(
            SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)
        )[1].performClick()
        waitForIdle()
        awaitIdle()
        onNodeWithText("Search Videos").assertIsDisplayed()
        onNodeWithText(testInput).assertIsDisplayed()
    }
}
