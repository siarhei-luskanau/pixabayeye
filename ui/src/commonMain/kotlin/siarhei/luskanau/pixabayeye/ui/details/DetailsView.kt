package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun DetailsView(detailsVewState: Flow<DetailsVewState>) {
    Text(
        text = "Details",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(16.dp),
    )
}
