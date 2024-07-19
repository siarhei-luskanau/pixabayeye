package siarhei.luskanau.pixabayeye.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PixabayBottomBar(onHomeClick: (() -> Unit)? = null, onLoginClick: (() -> Unit)? = null) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        content = {
            if (onHomeClick != null) {
                IconButton(onClick = { onHomeClick.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                }
            }
            if (onLoginClick != null) {
                IconButton(onClick = { onLoginClick.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
