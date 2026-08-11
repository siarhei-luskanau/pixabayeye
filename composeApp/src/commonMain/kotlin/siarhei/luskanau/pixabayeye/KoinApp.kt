package siarhei.luskanau.pixabayeye

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinConfiguration
import org.koin.plugin.module.dsl.koinConfiguration
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.navigation.NavApp

@Composable
fun KoinApp() = KoinApplication(
    configuration = KoinConfiguration {
        koinConfiguration<AppKoinApplication>().config.invoke(this)
    }
) {
    NavApp()
}

@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_NO)
@Preview(uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
internal fun KoinAppPreview() = AppTheme { KoinApp() }
