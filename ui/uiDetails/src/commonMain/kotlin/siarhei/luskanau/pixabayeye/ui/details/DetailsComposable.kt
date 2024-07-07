package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest

@Composable
fun DetailsComposable(largeImageUrl: String, tags: String, modifier: Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(largeImageUrl)
            .build(),
        contentDescription = tags,
        placeholder = ColorPainter(Color.Gray),
        error = ColorPainter(Color.Red),
        // onSuccess = { placeholder = it.result.memoryCacheKey },
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxSize()
    )
}
