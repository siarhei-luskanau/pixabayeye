package siarhei.luskanau.pixabayeye.ui.debug

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import kotlinx.serialization.Serializable
import org.koin.core.Koin

@Suppress("UNUSED_PARAMETER")
fun EntryProviderBuilder<NavKey>.debugGraph(backStack: MutableList<NavKey>, koin: Koin) {
    this.entry<DebugGraph> {
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

@Serializable
data object DebugGraph : NavKey

@Serializable
data object HomeDebugRoute : NavKey
