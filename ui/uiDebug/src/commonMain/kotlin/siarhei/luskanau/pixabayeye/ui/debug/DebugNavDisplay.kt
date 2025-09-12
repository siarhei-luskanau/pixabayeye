package siarhei.luskanau.pixabayeye.ui.debug

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.ui.debug.datastore.DatastoreScreen

@Preview
@Composable
internal fun DebugNavDisplay(
    koin: Koin,
    backStack: MutableList<NavKey>,
    innerPadding: PaddingValues
) {
    NavDisplay(
        modifier = Modifier.padding(innerPadding),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<DatastoreRoute> {
                DatastoreScreen(
                    viewModel = viewModel { koin.get { parametersOf() } }
                )
            }
            entry<NetworkRoute> {
                Text(
                    text = "Inspektify screen should be launched",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                LaunchedEffect(true) {
                    onStartInspektifyClicked()
                }
            }
        }
    )
}

sealed interface DebugRoutes : NavKey

@Serializable
data object DatastoreRoute : DebugRoutes

@Serializable
data object NetworkRoute : DebugRoutes

expect fun onStartInspektifyClicked()
