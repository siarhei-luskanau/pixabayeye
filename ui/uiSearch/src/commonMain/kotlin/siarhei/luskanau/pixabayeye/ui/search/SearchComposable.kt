package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyVerticalStaggeredGrid
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun SearchComposable(viewModel: SearchViewModel) {
    var searchTerm by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = null,
                onDebugScreenClick = { viewModel.onDebugScreenClicked() }
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
                modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("search_text_field")
            )
            PaginatedLazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 180.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                paginationState = viewModel.paginationState,
                firstPageProgressIndicator = { },
                newPageProgressIndicator = { },
                firstPageErrorIndicator = { e -> },
                newPageErrorIndicator = { e -> }
            ) {
                itemsIndexed(
                    viewModel.paginationState.allItems.orEmpty()
                ) { _, hitModel ->
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
        }
    }
}

@Preview
@Composable
internal fun SearchComposablePreview() = AppTheme {
    SearchComposable(
        viewModel = object : SearchViewModel() {
            override val paginationState: PaginationState<Int, HitModel> = PaginationState(
                initialPageKey = 1,
                onRequestPage = {
                    appendPage(
                        items = listOf(),
                        nextPageKey = 2,
                        isLastPage = true
                    )
                }
            )
            override fun onUpdateSearchTerm(searchTerm: String) = Unit
            override fun onImageClicked(hitModel: HitModel) = Unit
            override fun onDebugScreenClicked() = Unit
        }
    )
}
