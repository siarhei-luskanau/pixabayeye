package siarhei.luskanau.pixabayeye.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.bottombar_images
import siarhei.luskanau.pixabayeye.ui.common.resources.bottombar_videos

@Composable
fun PixabayBottomBar(
    onImagesClick: (() -> Unit)?,
    onVideosClick: (() -> Unit)?,
    selected: BottomBarSelected
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == BottomBarSelected.Images,
            onClick = { onImagesClick?.invoke() },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = stringResource(Res.string.bottombar_images)
                )
            },
            label = { Text(text = stringResource(Res.string.bottombar_images)) }
        )
        NavigationBarItem(
            selected = selected == BottomBarSelected.Videos,
            onClick = { onVideosClick?.invoke() },
            icon = {
                Icon(
                    imageVector = Icons.Filled.VideoLibrary,
                    contentDescription = stringResource(Res.string.bottombar_videos)
                )
            },
            label = { Text(text = stringResource(Res.string.bottombar_videos)) }
        )
    }
}

enum class BottomBarSelected { Images, Videos }

@Preview
@Composable
internal fun PixabayBottomBarImagesPreview() = AppTheme {
    PixabayBottomBar(
        onImagesClick = {},
        onVideosClick = {},
        selected = BottomBarSelected.Images
    )
}

@Preview
@Composable
internal fun PixabayBottomBarVideosPreview() = AppTheme {
    PixabayBottomBar(
        onImagesClick = {},
        onVideosClick = {},
        selected = BottomBarSelected.Videos
    )
}
