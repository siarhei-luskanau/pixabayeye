package siarhei.luskanau.pixabayeye.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import siarhei.luskanau.pixabayeye.ui.common.IS_DEBUG_SCREEN_ENABLED
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.back_button
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_arrow_back
import siarhei.luskanau.pixabayeye.ui.common.resources.ic_public
import siarhei.luskanau.pixabayeye.ui.common.theme.AppTheme

@Composable
fun PixabayTopAppBar(title: String, onBackClick: (() -> Unit)?, onDebugScreenClick: (() -> Unit)?) {
    @OptIn(ExperimentalMaterial3Api::class)
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (onDebugScreenClick != null && IS_DEBUG_SCREEN_ENABLED) {
                IconButton(onClick = onDebugScreenClick) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_public),
                        contentDescription = "Inspektify"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
internal fun PixabayTopAppBarPreview() = AppTheme {
    PixabayTopAppBar(
        title = "Title",
        onBackClick = null,
        onDebugScreenClick = null
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
internal fun PixabayTopAppBarPreviewNight() = AppTheme {
    PixabayTopAppBar(
        title = "Title",
        onBackClick = null,
        onDebugScreenClick = null
    )
}
