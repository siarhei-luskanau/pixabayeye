package siarhei.luskanau.pixabayeye.ui.image.details

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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.testData
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun ImageDetailsScreen(viewModelProvider: () -> ImageDetailsViewModel) {
    val viewModel = viewModel { viewModelProvider() }
    ImageDetailsContent(
        viewState = viewModel.viewState,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun ImageDetailsContent(
    viewState: StateFlow<ImageDetailsViewState>,
    onEvent: (ImageDetailsViewEvent) -> Unit
) {
    val viewState = viewState.collectAsState()
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = { onEvent(ImageDetailsViewEvent.NavigateBack) },
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
                ImageDetailsViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                is ImageDetailsViewState.Success -> AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(result.hitModel.imageModel?.largeImageUrl.orEmpty())
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

                is ImageDetailsViewState.Error -> Text(
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
        onEvent(ImageDetailsViewEvent.Launched)
    }
}

@Stable
@Composable
expect fun Modifier.zoomableExp(): Modifier

@Preview
@Composable
internal fun ImageDetailsLoadingContentPreview() = AppTheme {
    ImageDetailsContent(
        viewState = MutableStateFlow(ImageDetailsViewState.Loading),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun ImageDetailsSuccessContentPreview(hitModel: HitModel = testData) = AppTheme {
    ImageDetailsContent(
        viewState = MutableStateFlow(
            ImageDetailsViewState.Success(hitModel = hitModel)
        ),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun ImageDetailsErrorContentPreview() = AppTheme {
    ImageDetailsContent(
        viewState = MutableStateFlow(ImageDetailsViewState.Error(Error("Something went wrong"))),
        onEvent = {}
    )
}
