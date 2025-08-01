package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
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
import siarhei.luskanau.pixabayeye.core.network.testData
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_ai
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun VideoListScreen(
    viewModelProvider: () -> VideoListViewModel,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val viewModel = viewModel { viewModelProvider() }
    VideoListContent(
        paginationState = viewModel.paginationState,
        searchTermFlow = viewModel.searchTermFlow,
        onEvent = viewModel::onEvent,
        onImagesClick = onImagesClick,
        onVideosClick = onVideosClick
    )
}

@Composable
internal fun VideoListContent(
    paginationState: PaginationState<Int, HitModel>,
    searchTermFlow: Flow<String>,
    onEvent: (VideoListViewEvent) -> Unit,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val searchTerm by searchTermFlow.collectAsState("")
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = { onEvent(VideoListViewEvent.NavigateBack) },
                onDebugScreenClick = { onEvent(VideoListViewEvent.DebugScreenClicked) }
            )
        },
        bottomBar = {
            PixabayBottomBar(
                onImagesClick = { onImagesClick?.invoke(searchTerm) },
                onVideosClick = { onVideosClick?.invoke(searchTerm) },
                selected = BottomBarSelected.Videos
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(VideoListViewEvent.VideoClicked(hitModel = hitModel))
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(
                                        ratio = requireNotNull(viewModel.width).toFloat() /
                                            requireNotNull(viewModel.height).toFloat()
                                    )
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalPlatformContext.current)
                                        .data(viewModel.thumbnail)
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
                                // Video duration overlay
                                val durationText = formatVideoDuration(hitModel.duration)
                                if (durationText.isNotEmpty()) {
                                    Text(
                                        text = durationText,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .background(
                                                color = Color.Black.copy(alpha = 0.7f),
                                                shape = MaterialTheme.shapes.small
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            TagsContent(
                                tagsString = hitModel.tags,
                                onTagClick = { tag ->
                                    onEvent(VideoListViewEvent.TagClicked(tag = tag))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagsContent(tagsString: String?, onTagClick: (String) -> Unit) {
    val tags = tagsString.orEmpty().split(",").map { it.trim() }.filter { it.isNotEmpty() }
    if (tags.isNotEmpty()) {
        LazyRow {
            items(tags) { tag ->
                TextButton(
                    onClick = { onTagClick(tag) },
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = tag,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall
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
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}

/**
 * Formats video duration in seconds to a human-readable string
 * @param durationSeconds Duration in seconds, can be null
 * @return Formatted duration string (e.g., "3:05", "1:23:45") or empty string if null
 */
private fun formatVideoDuration(durationSeconds: Int?): String {
    if (durationSeconds == null || durationSeconds <= 0) {
        return ""
    }
    val hours = durationSeconds / 3600
    val minutes = (durationSeconds % 3600) / 60
    val seconds = durationSeconds % 60
    return when {
        hours > 0 ->
            "$hours" +
                ":${minutes.toString().padStart(2, '0')}" +
                ":${seconds.toString().padStart(2, '0')}"
        else -> "$minutes:${seconds.toString().padStart(2, '0')}"
    }
}
