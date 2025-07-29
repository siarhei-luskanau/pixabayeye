package siarhei.luskanau.pixabayeye.ui.video.list

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.BottomBarSelected
import siarhei.luskanau.pixabayeye.common.PixabayBottomBar
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.testData
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun VideoListScreen(
    viewModelProvider: () -> VideoListViewModel,
    onImagesClick: (() -> Unit)? = null,
    onVideosClick: (() -> Unit)? = null
) {
    val viewModel = viewModel { viewModelProvider() }
    VideoListContent(
        paginationState = viewModel.paginationState,
        onEvent = viewModel::onEvent,
        onImagesClick = onImagesClick,
        onVideosClick = onVideosClick
    )
}

@Composable
internal fun VideoListContent(
    paginationState: PaginationState<Int, HitModel>,
    onEvent: (VideoListViewEvent) -> Unit,
    onImagesClick: (() -> Unit)? = null,
    onVideosClick: (() -> Unit)? = null
) {
    var searchTerm by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = null,
                onDebugScreenClick = { onEvent(VideoListViewEvent.DebugScreenClicked) }
            )
        },
        bottomBar = {
            PixabayBottomBar(
                onImagesClick = onImagesClick,
                onVideosClick = onVideosClick,
                selected = BottomBarSelected.Videos
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    onEvent(VideoListViewEvent.UpdateSearchTerm(searchTerm = it))
                },
                label = { Text("Search Videos") },
                modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("search_video_text_field")
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
                    val viewModel = hitModel.videosModel.orEmpty().values.first()
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(viewModel.thumbnail)
                            .build(),
                        contentDescription = hitModel.tags,
                        placeholder = ColorPainter(Color.Gray),
                        error = ColorPainter(Color.Red),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                ratio = requireNotNull(viewModel.width).toFloat() /
                                    requireNotNull(viewModel.height).toFloat()
                            )
                            .clickable {
                                onEvent(VideoListViewEvent.VideoClicked(hitModel = hitModel))
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun VideoListContentPreview() = AppTheme {
    VideoListContent(
        paginationState = PaginationState(
            initialPageKey = 1,
            onRequestPage = {
                appendPage(
                    items = listOf(testData),
                    nextPageKey = 2,
                    isLastPage = true
                )
            }
        ),
        onEvent = {}
    )
}
