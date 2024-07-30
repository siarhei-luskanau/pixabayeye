package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import siarhei.luskanau.pixabayeye.common.PixabayBottomBar
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun SearchComposable(
    searchVewStateFlow: Flow<SearchVewState>,
    onUpdateSearchTerm: suspend (String) -> Unit,
    onImageClicked: (HitModel) -> Unit,
    onHomeClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val vewState = searchVewStateFlow.collectAsState(initial = null)
    val lazyPagingItems: LazyPagingItems<HitModel> =
        (vewState.value?.pagingDataFlow ?: emptyFlow()).collectAsLazyPagingItems()
    var searchTerm by remember { mutableStateOf("") }
    Scaffold(
        topBar = { PixabayTopAppBar(title = stringResource(Res.string.screen_name_search)) },
        bottomBar = {
            PixabayBottomBar(
                onHomeClick = onHomeClick,
                onLoginClick = onLoginClick
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    coroutineScope.launch { onUpdateSearchTerm.invoke(it) }
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(180.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = lazyPagingItems.itemKey { it.imageId },
                        contentType = lazyPagingItems.itemContentType { null }
                    ) { index ->
                        lazyPagingItems[index]?.let { hitModel ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                    .data(hitModel.middleImageUrl)
                                    .build(),
                                contentDescription = hitModel.tags,
                                placeholder = ColorPainter(Color.Gray),
                                error = ColorPainter(Color.Red),
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(
                                        ratio =
                                        hitModel.middleImageWidth.toFloat() /
                                            hitModel.middleImageHeight.toFloat()
                                    )
                                    .clickable { onImageClicked.invoke(hitModel) }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
