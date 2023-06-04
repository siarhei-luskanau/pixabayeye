package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import com.seiko.imageloader.rememberAsyncImagePainter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import siarhei.luskanau.pixabayeye.network.HitModel

@Composable
fun SearchView(
    searchVewStateFlow: Flow<SearchVewState>,
    onUpdateSearchTerm: suspend (String) -> Unit,
    onImageClicked: (HitModel) -> Unit,
    modifier: Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val loginVewState = searchVewStateFlow.collectAsState(initial = null)
    val items = (loginVewState.value?.pager?.flow ?: emptyFlow()).collectAsLazyPagingItems()
    var searchTerm by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        @OptIn(ExperimentalMaterial3Api::class)
        OutlinedTextField(
            value = searchTerm,
            onValueChange = {
                searchTerm = it
                coroutineScope.launch {
                    onUpdateSearchTerm.invoke(it)
                }
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey { it.imageId },
                contentType = items.itemContentType { null },
            ) { index ->
                items[index]?.let { hitModel ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onImageClicked.invoke(hitModel)
                            },
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(url = hitModel.previewUrl),
                            contentDescription = hitModel.tags,
                            modifier = Modifier
                                .height(hitModel.previewHeight.dp)
                                .width(hitModel.previewWidth.dp),
                        )
                        Column {
                            Text(
                                text = hitModel.userName,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp),
                            )
                            Text(
                                text = hitModel.tags,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
