package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun DetailsComposable(viewModel: DetailsViewModel) {
    val viewState = viewModel.viewState.collectAsState()
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = { viewModel.onBackClick() },
                onDebugScreenClick = null
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            when (val result = viewState.value) {
                DetailsViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                is DetailsViewState.Success -> AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(result.hitModel.largeImageUrl)
                        .build(),
                    contentDescription = result.hitModel.tags,
                    placeholder = ColorPainter(Color.Gray),
                    error = ColorPainter(Color.Red),
                    // onSuccess = { placeholder = it.result.memoryCacheKey },
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .zoomableExp()
                )

                is DetailsViewState.Error -> Text(
                    text = "Something went wrong\n${result.error.message.orEmpty()}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.onLaunched()
    }
}

@Stable
@Composable
expect fun Modifier.zoomableExp(): Modifier

@Preview
@Composable
internal fun DetailsLoadingComposablePreview() = AppTheme {
    DetailsComposable(
        viewModel = detailsViewModel(DetailsViewState.Loading)
    )
}

@Preview
@Composable
internal fun DetailsSuccessComposablePreview() = AppTheme {
    DetailsComposable(
        viewModel = detailsViewModel(
            DetailsViewState.Success(
                HitModel(
                    imageId = 123,
                    userId = 456,
                    userName = "John Doe",
                    tags = "tag1, tag2, tag3",
                    likes = 100,
                    downloads = 200,
                    comments = 300,
                    previewUrl = "https://example.com/preview.jpg",
                    previewHeight = 100,
                    previewWidth = 100,
                    middleImageUrl = "https://example.com/middle.jpg",
                    middleImageHeight = 200,
                    middleImageWidth = 200,
                    largeImageUrl = "https://example.com/large.jpg"
                )
            )
        )
    )
}

@Preview
@Composable
internal fun DetailsErrorComposablePreview() = AppTheme {
    DetailsComposable(
        viewModel = detailsViewModel(DetailsViewState.Error(Error("Something went wrong")))
    )
}

internal fun detailsViewModel(detailsViewState: DetailsViewState): DetailsViewModel =
    object : DetailsViewModel() {
        override val viewState: StateFlow<DetailsViewState> = MutableStateFlow(detailsViewState)
        override fun onLaunched() = Unit
        override fun onBackClick() = Unit
    }
