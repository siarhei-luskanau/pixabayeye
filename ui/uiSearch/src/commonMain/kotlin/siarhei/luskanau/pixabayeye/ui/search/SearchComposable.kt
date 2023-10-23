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
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import siarhei.luskanau.pixabayeye.core.network.HitModel

@Composable
fun SearchComposable(
    searchVewStateFlow: Flow<SearchVewState>,
    onUpdateSearchTerm: suspend (String) -> Unit,
    onImageClicked: (HitModel) -> Unit,
    modifier: Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val loginVewState = searchVewStateFlow.collectAsState(initial = null)
    val lazyPagingItems: LazyPagingItems<HitModel> = (loginVewState.value?.pagingDataFlow ?: emptyFlow()).collectAsLazyPagingItems()
    var searchTerm by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
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
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.imageId },
                contentType = lazyPagingItems.itemContentType { null },
            ) { index ->
                lazyPagingItems[index]?.let { hitModel ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onImageClicked.invoke(hitModel)
                                },
                    ) {
                        val action by rememberImageAction(url = hitModel.previewUrl)
                        Image(
                            painter = rememberImageActionPainter(action = action),
                            contentDescription = hitModel.tags,
                            modifier =
                                Modifier
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
//            item {
//                when {
//                    lazyPagingItems.loadState.source.append is LoadStateError -> Button(
//                        onClick = { lazyPagingItems.retry() },
//                    ) {
//                        Text("Retry")
//                    }
//                    lazyPagingItems.loadState.source.append is LoadStateLoading -> CircularProgressIndicator()
//                    else -> Spacer(Modifier.height(12.dp))
//                }
//            }
        }
    }
}
