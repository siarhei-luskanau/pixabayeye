package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import siarhei.luskanau.pixabayeye.common.PixabayTopAppBar
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.bottombar_images
import siarhei.luskanau.pixabayeye.ui.common.resources.bottombar_videos
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_image
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_video_library

private val WindowWidthLarge = 1200.dp

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AppNavigationSuiteScaffold(
    selectedMediaType: MediaType?,
    onImagesClick: () -> Unit,
    onVideosClick: () -> Unit,
    title: String,
    onBackClick: (() -> Unit)?,
    onDebugScreenClick: (() -> Unit)?,
    content: @Composable () -> Unit
) {
    val windowSize = LocalWindowInfo.current.containerDpSize
    val navLayoutType =
        if (windowSize.width >= WindowWidthLarge) {
            NavigationSuiteType.NavigationDrawer
        } else {
            val adaptiveInfo = currentWindowAdaptiveInfoV2()
            when (
                val type = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                    adaptiveInfo
                )
            ) {
                NavigationSuiteType.NavigationBar -> type
                else -> NavigationSuiteType.NavigationRail
            }
        }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                label = { Text(stringResource(Res.string.bottombar_images)) },
                icon = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_image),
                        contentDescription = stringResource(Res.string.bottombar_images)
                    )
                },
                selected = selectedMediaType == MediaType.IMAGE,
                onClick = onImagesClick
            )
            item(
                label = { Text(stringResource(Res.string.bottombar_videos)) },
                icon = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_video_library),
                        contentDescription = stringResource(Res.string.bottombar_videos)
                    )
                },
                selected = selectedMediaType == MediaType.VIDEO,
                onClick = onVideosClick
            )
        },
        layoutType = navLayoutType
    ) {
        Scaffold(
            topBar = {
                PixabayTopAppBar(
                    title = title,
                    onBackClick = onBackClick,
                    onDebugScreenClick = onDebugScreenClick
                )
            }
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                content()
            }
        }
    }
}

@Preview(name = "Light", uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Preview(name = "Small", widthDp = 360, heightDp = 640)
@Preview(name = "Medium", widthDp = 700, heightDp = 840)
@Preview(name = "Large", widthDp = 1280, heightDp = 800)
@Composable
private fun AppNavigationSuiteScaffoldPreview() = AppTheme {
    AppNavigationSuiteScaffold(
        selectedMediaType = MediaType.IMAGE,
        onImagesClick = {},
        onVideosClick = {},
        title = "Title",
        onBackClick = {},
        onDebugScreenClick = null
    ) {
        Text("Content")
    }
}
