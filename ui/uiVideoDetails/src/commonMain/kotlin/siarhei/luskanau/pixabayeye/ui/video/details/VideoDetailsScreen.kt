package siarhei.luskanau.pixabayeye.ui.video.details

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState
import kotlin.collections.orEmpty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.network.testData
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun VideoDetailsScreen(viewModelProvider: () -> VideoDetailsViewModel) {
    val viewModel = viewModel { viewModelProvider() }
    VideoDetailsContent(
        viewState = viewModel.viewState,
        onEvent = viewModel::onEvent
    )
}

@Composable
internal fun VideoDetailsContent(
    viewState: StateFlow<VideoDetailsViewState>,
    onEvent: (VideoDetailsViewEvent) -> Unit
) {
    val viewState = viewState.collectAsState()
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = { onEvent(VideoDetailsViewEvent.NavigateBack) },
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
                VideoDetailsViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                is VideoDetailsViewState.Success -> {
                    val videoModel = result.hitModel.videosModel.orEmpty().values.first()
                    val playerState = rememberVideoPlayerState()
                    LaunchedEffect(videoModel.url) {
                        playerState.openUri(videoModel.url)
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!result.isTest) {
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
                            Button(onClick = { playerState.play() }) { Text("Play") }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { playerState.pause() }) { Text("Pause") }
                        }
                    }
                }

                is VideoDetailsViewState.Error -> Text(
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
        onEvent(VideoDetailsViewEvent.Launched)
    }
}

@Preview
@Composable
internal fun VideoDetailsLoadingContentPreview() = AppTheme {
    VideoDetailsContent(
        viewState = MutableStateFlow(VideoDetailsViewState.Loading),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoDetailsSuccessContentPreview() = AppTheme {
    VideoDetailsContent(
        viewState = MutableStateFlow(
            VideoDetailsViewState.Success(hitModel = testData, isTest = true)
        ),
        onEvent = {}
    )
}

@Preview
@Composable
internal fun VideoDetailsErrorContentPreview() = AppTheme {
    VideoDetailsContent(
        viewState = MutableStateFlow(VideoDetailsViewState.Error(Error("Something went wrong"))),
        onEvent = {}
    )
}
