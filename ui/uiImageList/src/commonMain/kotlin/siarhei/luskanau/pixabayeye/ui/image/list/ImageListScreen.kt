package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyVerticalStaggeredGrid
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.BottomBarSelected
import siarhei.luskanau.pixabayeye.common.PixabayBottomBar
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_ai
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun ImageListScreen(
    viewModelProvider: () -> ImageListViewModel,
    onImagesClick: (() -> Unit)? = null,
    onVideosClick: (() -> Unit)? = null
) {
    val viewModel = viewModel { viewModelProvider() }
    ImageListContent(
        paginationState = viewModel.paginationState,
        searchTermFlow = viewModel.searchTermFlow,
        onEvent = viewModel::onEvent,
        onImagesClick = onImagesClick,
        onVideosClick = onVideosClick
    )
}

@Composable
internal fun ImageListContent(
    paginationState: PaginationState<Int, HitModel>,
    searchTermFlow: Flow<String>,
    onEvent: (ImageListViewEvent) -> Unit,
    onImagesClick: (() -> Unit)? = null,
    onVideosClick: (() -> Unit)? = null
) {
    val searchTerm by searchTermFlow.collectAsState("")
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = null,
                onDebugScreenClick = { onEvent(ImageListViewEvent.DebugScreenClicked) }
            )
        },
        bottomBar = {
            PixabayBottomBar(
                onImagesClick = onImagesClick,
                onVideosClick = onVideosClick,
                selected = BottomBarSelected.Images
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    onEvent(ImageListViewEvent.UpdateSearchTerm(searchTerm = it))
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("search_text_field")
            )
            PaginatedLazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 180.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                paginationState = paginationState,
                firstPageProgressIndicator = { },
                newPageProgressIndicator = { },
                firstPageErrorIndicator = { e -> },
                newPageErrorIndicator = { e -> }
            ) {
                itemsIndexed(
                    paginationState.allItems.orEmpty()
                ) { _, hitModel ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                ratio =
                                requireNotNull(
                                    hitModel.imageModel?.middleImageWidth
                                ).toFloat() /
                                    requireNotNull(
                                        hitModel.imageModel?.middleImageHeight
                                    ).toFloat()
                            )
                            .clickable {
                                onEvent(ImageListViewEvent.ImageClicked(hitModel = hitModel))
                            }
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(hitModel.imageModel?.middleImageUrl.orEmpty())
                                .build(),
                            contentDescription = hitModel.tags,
                            placeholder = ColorPainter(Color.Gray),
                            error = ColorPainter(Color.Red),
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (hitModel.isAiGenerated) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_ai),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(24.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun ImageListContentPreview() = AppTheme {
    ImageListContent(
        paginationState = PaginationState(
            initialPageKey = 1,
            onRequestPage = {
                appendPage(
                    items = listOf(),
                    nextPageKey = 2,
                    isLastPage = true
                )
            }
        ),
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}
