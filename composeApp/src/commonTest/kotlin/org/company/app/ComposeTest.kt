package org.company.app

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.KoinApp

@OptIn(ExperimentalTestApi::class)
class ComposeTest {

    @Test
    fun simpleCheck() = runComposeUiTest {
        setContent {
            KoinApp()
        }
        val testInput = "test123abc"
        onNodeWithTag("search_text_field").apply {
            performTextInput(text = testInput)
        }
        onNodeWithTag("search_text_field").assertTextContains(value = testInput)
    }
}
