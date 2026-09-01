package siarhei.luskanau.pixabayeye.ui.media.details

sealed interface MediaDetailsViewEvent {
    data object Launched : MediaDetailsViewEvent
    data object NavigateBack : MediaDetailsViewEvent
}
