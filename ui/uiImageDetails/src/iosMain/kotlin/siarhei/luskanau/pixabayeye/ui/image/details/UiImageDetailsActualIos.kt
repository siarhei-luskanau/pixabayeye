package siarhei.luskanau.pixabayeye.ui.image.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Stable
@Composable
actual fun Modifier.zoomableExp() = this.zoomable(rememberZoomState())
