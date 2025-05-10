package org.company.app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.KoinApp

@OptIn(ExperimentalTestApi::class)
class ComposeTest {

    @Test
    fun simpleCheck() = runComposeUiTest {
        setContent {
            // workaround "CompositionLocal LocalLifecycleOwner not present"
            // https://youtrack.jetbrains.com/issue/CMP-7419/CompositionLocal-LocalLifecycleOwner-not-present-on-iosSimulatorArm64Test-task
            CompositionLocalProvider(
                LocalLifecycleOwner provides LocalLifecycleOwnerFake()
            ) {
                KoinApp()
            }
        }
        val testInput = "test123abc"
        onNodeWithTag("search_text_field").apply {
            performTextInput(text = testInput)
        }
        onNodeWithTag("search_text_field").assertTextContains(value = testInput)
    }
}

private class LocalLifecycleOwnerFake : LifecycleOwner {
    override val lifecycle: Lifecycle = LifecycleRegistry(this).apply {
        currentState = Lifecycle.State.RESUMED
    }
}
