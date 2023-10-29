package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import siarhei.luskanau.pixabayeye.core.network.HitModel

@Composable
fun DetailsComposable(hitModel: HitModel, modifier: Modifier) {
    val action by rememberImageAction(url = hitModel.largeImageUrl)
    Image(
        painter = rememberImageActionPainter(action = action),
        modifier = modifier
            .fillMaxSize(),
        contentDescription = null
    )
}
