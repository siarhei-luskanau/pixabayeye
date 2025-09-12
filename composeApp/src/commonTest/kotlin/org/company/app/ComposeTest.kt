package org.company.app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.KoinApp

@OptIn(ExperimentalTestApi::class)
class ComposeTest {

    @Test
    fun simpleCheck() = runComposeUiTest {
        setContent {
            // CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides LocalNavigationEventDispatcherOwnerFake()) {
            KoinApp()
            // }
        }
        onRoot().printToLog("StartTag")
        val testInput = "test123abc"
        onNodeWithTag("search_text_field1").apply {
            performTextInput(text = testInput)
            assertTextContains(value = testInput)
        }
        onNodeWithText("Videos").performClick()
        onNodeWithText(testInput).assertIsDisplayed()
    }
}

private class LocalNavigationEventDispatcherOwnerFake : NavigationEventDispatcherOwner {
    override val navigationEventDispatcher: NavigationEventDispatcher = NavigationEventDispatcher()
}
