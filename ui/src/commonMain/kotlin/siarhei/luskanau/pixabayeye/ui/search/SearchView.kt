package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.Pager
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import siarhei.luskanau.pixabayeye.network.HitModel

@Composable
fun SearchView(
    pager: Pager<Int, HitModel>,
) {
    val items = pager.flow.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.imageId },
            contentType = items.itemContentType { null },
        ) { index ->
            items[index]?.let { hitModel ->
                Text(
                    text = hitModel.userName,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
