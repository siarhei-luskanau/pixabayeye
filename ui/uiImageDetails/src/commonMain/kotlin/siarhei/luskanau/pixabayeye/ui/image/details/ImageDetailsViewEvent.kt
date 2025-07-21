package siarhei.luskanau.pixabayeye.ui.image.details

sealed interface ImageDetailsViewEvent {
    data object Launched : ImageDetailsViewEvent
    data object NavigateBack : ImageDetailsViewEvent
}
