package siarhei.luskanau.pixabayeye.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seiko.imageloader.rememberAsyncImagePainter
import siarhei.luskanau.pixabayeye.network.HitModel

@Composable
fun DetailsView(hitModel: HitModel) {
    Image(
        painter = rememberAsyncImagePainter(url = hitModel.largeImageUrl),
        modifier = Modifier
            .fillMaxSize(),
        contentDescription = null,
    )
}
