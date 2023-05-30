package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.Pager
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import com.seiko.imageloader.rememberAsyncImagePainter
import siarhei.luskanau.pixabayeye.network.HitModel

@Composable
fun SearchView(
    pager: Pager<Int, HitModel>,
    onImageClicked: (HitModel) -> Unit,
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
