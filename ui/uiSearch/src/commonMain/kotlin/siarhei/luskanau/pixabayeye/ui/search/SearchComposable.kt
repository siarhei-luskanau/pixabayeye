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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.cash.paging.PagingData
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayBottomBar
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun SearchComposable(viewModel: SearchViewModel) {
    val lazyPagingItems: LazyPagingItems<HitModel> =
        viewModel.getPagingFlow().collectAsLazyPagingItems()
    var searchTerm by remember { mutableStateOf("") }
    Scaffold(
        topBar = { PixabayTopAppBar(title = stringResource(Res.string.screen_name_search)) },
        bottomBar = {
            PixabayBottomBar(
                onHomeClick = { viewModel.onHomeClick() },
                onLoginClick = { viewModel.onLoginClick() }
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    viewModel.onUpdateSearchTerm(searchTerm = it)
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
                                    .clickable { viewModel.onImageClicked(hitModel) }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun SearchComposablePreview() = SearchComposable(
    viewModel = object : SearchViewModel() {
        override fun getPagingFlow(): Flow<PagingData<HitModel>> = flowOf(PagingData.empty())
        override fun onUpdateSearchTerm(searchTerm: String) = Unit
        override fun onHomeClick() = Unit
        override fun onLoginClick() = Unit
        override fun onImageClicked(hitModel: HitModel) = Unit
    }
)
