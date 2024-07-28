package siarhei.luskanau.pixabayeye.ui.search

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.flowOf

// @Preview
@Composable
fun SearchComposablePreview() = SearchComposable(
    searchVewStateFlow = flowOf(
        SearchVewState(
            pagingDataFlow = flowOf()
        )
    ),
    onUpdateSearchTerm = {},
    onImageClicked = {},
    onHomeClick = {},
    onLoginClick = {}
)
