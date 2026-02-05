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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import siarhei.luskanau.pixabayeye.common.BottomBarSelected
import siarhei.luskanau.pixabayeye.common.PixabayBottomBar
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.paging.ErrorContent
import siarhei.luskanau.pixabayeye.common.paging.ErrorItem
import siarhei.luskanau.pixabayeye.common.paging.LoadingContent
import siarhei.luskanau.pixabayeye.common.paging.LoadingItem
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.testData
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_ai
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun VideoListScreen(
    key: String,
    viewModelProvider: () -> VideoListViewModel,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val viewModel = viewModel(key = key) { viewModelProvider() }
    VideoListContent(
        pagingDataFlow = viewModel.pagingDataFlow,
        searchTermFlow = viewModel.searchTermFlow,
        onEvent = viewModel::onEvent,
        onImagesClick = onImagesClick,
        onVideosClick = onVideosClick
    )
}

@Composable
internal fun VideoListContent(
    pagingDataFlow: Flow<PagingData<HitModel>>,
    searchTermFlow: Flow<String>,
    onEvent: (VideoListViewEvent) -> Unit,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val searchTerm by searchTermFlow.collectAsState("")
    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
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

            // Handle refresh state (initial load)
            when (val refreshState = lazyPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingContent()
                }

                is LoadState.Error -> {
                    ErrorContent(
                        error = refreshState.error,
                        onRetry = { lazyPagingItems.retry() }
                    )
                }

                is LoadState.NotLoading -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 180.dp),
                        verticalItemSpacing = 4.dp,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Handle prepend state (loading previous items)
                        when (val prependState = lazyPagingItems.loadState.prepend) {
                            is LoadState.Loading -> {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    LoadingItem()
                                }
                            }

                            is LoadState.Error -> {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    ErrorItem(
                                        error = prependState.error,
                                        onRetry = { lazyPagingItems.retry() }
                                    )
                                }
                            }

                            is LoadState.NotLoading -> Unit
                        }

                        items(lazyPagingItems.itemCount) { index ->
                            val hitModel = lazyPagingItems[index] ?: return@items
                            val videoModel = hitModel.videosModel.orEmpty()["tiny"]
                                ?: hitModel.videosModel.orEmpty()["small"]
                                ?: hitModel.videosModel.orEmpty().values.first()
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(
                                            VideoListViewEvent.VideoClicked(hitModel = hitModel)
                                        )
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(
                                                ratio = requireNotNull(videoModel.width).toFloat() /
                                                    requireNotNull(videoModel.height).toFloat()
                                            )
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(
                                                LocalPlatformContext.current
                                            )
                                                .data(videoModel.thumbnail)
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

                        // Handle append state (loading more items)
                        when (val appendState = lazyPagingItems.loadState.append) {
                            is LoadState.Loading -> {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    LoadingItem()
                                }
                            }

                            is LoadState.Error -> {
                                item(span = StaggeredGridItemSpan.FullLine) {
                                    ErrorItem(
                                        error = appendState.error,
                                        onRetry = { lazyPagingItems.retry() }
                                    )
                                }
                            }

                            is LoadState.NotLoading -> Unit
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

@Preview
@Composable
internal fun VideoListContentRefreshIsLoadingPreview() = AppTheme {
    VideoListContent(
        pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false)
                )
            )
        ),
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoListContentRefreshIsErrorPreview() = AppTheme {
    VideoListContent(
        pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Error("Something went wrong")),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false)
                )
            )
        ),
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoListContentDataPresentAndNotLoadingPreview(
    hitList: List<HitModel> = listOf(testData)
) = AppTheme {
    VideoListContent(
        pagingDataFlow = flowOf(
            PagingData.from(
                data = hitList,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false)
                )
            )
        ),
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoListContentDataAbsentAndNotLoadingPreview() = AppTheme {
    VideoListContent(
        pagingDataFlow = flowOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = false),
                    append = LoadState.NotLoading(endOfPaginationReached = false)
                )
            )
        ),
        searchTermFlow = flowOf("Search text"),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoListContentPrependLoadingPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        VideoListContent(
            pagingDataFlow = flowOf(
                PagingData.from(
                    data = hitList.take(1),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(endOfPaginationReached = false),
                        prepend = LoadState.Loading,
                        append = LoadState.NotLoading(endOfPaginationReached = false)
                    )
                )
            ),
            searchTermFlow = flowOf("Search text"),
            onEvent = {}
        )
    }

@Preview
@Composable
internal fun VideoListContentPrependErrorPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        VideoListContent(
            pagingDataFlow = flowOf(
                PagingData.from(
                    data = hitList.take(1),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(endOfPaginationReached = false),
                        prepend = LoadState.Error(Error("Something went wrong")),
                        append = LoadState.NotLoading(endOfPaginationReached = false)
                    )
                )
            ),
            searchTermFlow = flowOf("Search text"),
            onEvent = {}
        )
    }

@Preview
@Composable
internal fun VideoListContentAppendLoadingPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        VideoListContent(
            pagingDataFlow = flowOf(
                PagingData.from(
                    data = hitList.take(1),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(endOfPaginationReached = false),
                        prepend = LoadState.NotLoading(endOfPaginationReached = false),
                        append = LoadState.Loading
                    )
                )
            ),
            searchTermFlow = flowOf("Search text"),
            onEvent = {}
        )
    }

@Preview
@Composable
internal fun VideoListContentAppendErrorPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        VideoListContent(
            pagingDataFlow = flowOf(
                PagingData.from(
                    data = hitList.take(1),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(endOfPaginationReached = false),
                        prepend = LoadState.NotLoading(endOfPaginationReached = false),
                        append = LoadState.Error(Error("Something went wrong"))
                    )
                )
            ),
            searchTermFlow = flowOf("Search text"),
            onEvent = {}
        )
    }
