package siarhei.luskanau.pixabayeye.ui.debug

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.ui.debug.datastore.DatastoreComposable

@Preview
@Composable
internal fun DebugNavHost(
    koin: Koin,
    navHostController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = DatastoreRoute,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<DatastoreRoute> {
            DatastoreComposable(
                viewModel = viewModel { koin.get { parametersOf() } }
            )
        }
        composable<NetworkRoute> {
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
}

sealed interface DebugRoutes

@Serializable
data object DatastoreRoute : DebugRoutes

@Serializable
data object NetworkRoute : DebugRoutes

expect fun onStartInspektifyClicked()
