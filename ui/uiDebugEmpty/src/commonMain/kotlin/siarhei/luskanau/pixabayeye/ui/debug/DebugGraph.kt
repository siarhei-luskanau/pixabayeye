package siarhei.luskanau.pixabayeye.ui.debug

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import org.koin.core.Koin

fun NavGraphBuilder.debugGraph(@Suppress("UNUSED_PARAMETER") koin: Koin) {
    navigation<DebugGraph>(startDestination = HomeDebugRoute) {
        composable<HomeDebugRoute> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Debug Empty",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Serializable
data object DebugGraph

sealed interface DebugRoutes

@Serializable
data object HomeDebugRoute : DebugRoutes
