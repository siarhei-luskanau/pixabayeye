package siarhei.luskanau.pixabayeye.ui.media.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import io.github.kdroidfilter.composemediaplayer.VideoPlayerState
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.testData

@Composable
fun MediaDetailsScreen(key: String, viewModelProvider: () -> MediaDetailsViewModel) {
    val viewModel = viewModel(key = key) { viewModelProvider() }
    MediaDetailsContent(
        viewState = viewModel.viewState,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun MediaDetailsContent(
    viewState: StateFlow<MediaDetailsViewState>,
    onEvent: (MediaDetailsViewEvent) -> Unit
) {
    val viewState = viewState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (val result = viewState.value) {
            MediaDetailsViewState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            is MediaDetailsViewState.Success -> if (result.hitModel.imageModel != null) {
                ImageDetailsContent(hitModel = result.hitModel)
            } else {
                VideoDetailsContent(hitModel = result.hitModel, isTest = result.isTest)
            }

            is MediaDetailsViewState.Error -> Text(
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
    LaunchedEffect(Unit) {
        onEvent(MediaDetailsViewEvent.Launched)
    }
}

@Composable
private fun ImageDetailsContent(hitModel: HitModel) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(hitModel.imageModel?.largeImageUrl.orEmpty())
            .build(),
        contentDescription = hitModel.tags,
        placeholder = ColorPainter(Color.Gray),
        error = ColorPainter(Color.Red),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
            .zoomable(rememberZoomState())
    )
}

@Composable
private fun VideoDetailsContent(hitModel: HitModel, isTest: Boolean) {
    val videoModel = hitModel.videosModel.orEmpty().values.first()
    val playerState: VideoPlayerState? = if (!isTest) {
        rememberVideoPlayerState()
    } else {
        null
    }
    LaunchedEffect(videoModel.url) {
        playerState?.openUri(videoModel.url)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (playerState != null) {
                VideoPlayerSurface(
                    playerState = playerState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { playerState?.play() }) { Text("Play") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { playerState?.pause() }) { Text("Pause") }
        }
    }
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsImageLoadingPreview() = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(MediaDetailsViewState.Loading),
        onEvent = {}
    )
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsImageSuccessPreview(hitModel: HitModel = testData) = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(
            MediaDetailsViewState.Success(hitModel = hitModel)
        ),
        onEvent = {}
    )
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsImageErrorPreview() = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(MediaDetailsViewState.Error(Error("Something went wrong"))),
        onEvent = {}
    )
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsVideoLoadingPreview() = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(MediaDetailsViewState.Loading),
        onEvent = {}
    )
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsVideoSuccessPreview(
    hitModel: HitModel = testData.copy(imageModel = null)
) = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(
            MediaDetailsViewState.Success(hitModel = hitModel, isTest = true)
        ),
        onEvent = {}
    )
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun MediaDetailsVideoErrorPreview() = AppTheme {
    MediaDetailsContent(
        viewState = MutableStateFlow(MediaDetailsViewState.Error(Error("Something went wrong"))),
        onEvent = {}
    )
}
