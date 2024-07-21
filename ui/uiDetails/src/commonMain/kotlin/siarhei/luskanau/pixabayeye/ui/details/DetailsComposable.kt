package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import org.jetbrains.compose.resources.stringResource
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search

@Composable
fun DetailsComposable(largeImageUrl: String, tags: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            PixabayTopAppBar(
                title = stringResource(Res.string.screen_name_search),
                onBackClick = onBackClick
            )
        }
    ) { contentPadding ->
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(largeImageUrl)
                .build(),
            contentDescription = tags,
            placeholder = ColorPainter(Color.Gray),
            error = ColorPainter(Color.Red),
            // onSuccess = { placeholder = it.result.memoryCacheKey },
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        )
    }
}
