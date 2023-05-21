package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchView(searchVewState: Flow<SearchVewState>) {
    Text(
        text = "Search",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(16.dp),
    )
}
