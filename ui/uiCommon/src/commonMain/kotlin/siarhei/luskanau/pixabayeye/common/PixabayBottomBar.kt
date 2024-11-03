package siarhei.luskanau.pixabayeye.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PixabayBottomBar(onHomeClick: (() -> Unit)? = null, onLoginClick: (() -> Unit)? = null) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (onHomeClick != null) {
            NavigationBarItem(
                selected = true,
                onClick = { onHomeClick.invoke() },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
            )
        }
        NavigationBarItem(
            selected = false,
            onClick = { onStartInspektifyClicked() },
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
        )
        if (onLoginClick != null) {
            NavigationBarItem(
                selected = false,
                onClick = { onLoginClick.invoke() },
                icon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = null) }
            )
        }
    }
}

expect fun onStartInspektifyClicked()
