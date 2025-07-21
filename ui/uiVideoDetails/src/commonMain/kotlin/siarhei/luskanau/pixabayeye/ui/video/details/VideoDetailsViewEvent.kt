package siarhei.luskanau.pixabayeye.ui.video.details

sealed interface VideoDetailsViewEvent {
    data object Launched : VideoDetailsViewEvent
    data object NavigateBack : VideoDetailsViewEvent
}
