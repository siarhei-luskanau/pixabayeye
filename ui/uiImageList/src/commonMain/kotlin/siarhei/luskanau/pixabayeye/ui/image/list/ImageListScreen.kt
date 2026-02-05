package siarhei.luskanau.pixabayeye.ui.image.list

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
fun ImageListScreen(
    key: String,
    viewModelProvider: () -> ImageListViewModel,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val viewModel = viewModel(key = key) { viewModelProvider() }
    ImageListContent(
        pagingDataFlow = viewModel.pagingDataFlow,
        searchTermFlow = viewModel.searchTermFlow,
        onEvent = viewModel::onEvent,
        onImagesClick = onImagesClick,
        onVideosClick = onVideosClick
    )
}

@Composable
internal fun ImageListContent(
    pagingDataFlow: Flow<PagingData<HitModel>>,
    searchTermFlow: Flow<String>,
    onEvent: (ImageListViewEvent) -> Unit,
    onImagesClick: ((String?) -> Unit)? = null,
    onVideosClick: ((String?) -> Unit)? = null
) {
    val searchTerm by searchTermFlow.collectAsState("")
    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = { onEvent(ImageListViewEvent.NavigateBack) },
                onDebugScreenClick = { onEvent(ImageListViewEvent.DebugScreenClicked) }
            )
        },
        bottomBar = {
            PixabayBottomBar(
                onImagesClick = { onImagesClick?.invoke(searchTerm) },
                onVideosClick = { onVideosClick?.invoke(searchTerm) },
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
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(
                                            ImageListViewEvent.ImageClicked(hitModel = hitModel)
                                        )
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(
                                                ratio =
                                                    requireNotNull(
                                                        hitModel.imageModel?.webFormatWidth
                                                    ).toFloat() /
                                                        requireNotNull(
                                                            hitModel.imageModel?.webFormatHeight
                                                        ).toFloat()
                                            )
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(
                                                LocalPlatformContext.current
                                            )
                                                .data(hitModel.imageModel?.webFormatUrl.orEmpty())
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
                                    TagsContent(
                                        tagsString = hitModel.tags,
                                        onTagClick = { tag ->
                                            onEvent(ImageListViewEvent.TagClicked(tag = tag))
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

@Preview
@Composable
internal fun ImageListContentRefreshIsLoadingPreview() = AppTheme {
    ImageListContent(
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
internal fun ImageListContentRefreshIsErrorPreview() = AppTheme {
    ImageListContent(
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
internal fun ImageListContentDataPresentAndNotLoadingPreview(
    hitList: List<HitModel> = listOf(testData)
) = AppTheme {
    ImageListContent(
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
internal fun ImageListContentDataAbsentAndNotLoadingPreview() = AppTheme {
    ImageListContent(
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
internal fun ImageListContentPrependLoadingPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        ImageListContent(
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
internal fun ImageListContentPrependErrorPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        ImageListContent(
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
internal fun ImageListContentAppendLoadingPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        ImageListContent(
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
internal fun ImageListContentAppendErrorPreview(hitList: List<HitModel> = listOf(testData)) =
    AppTheme {
        ImageListContent(
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
