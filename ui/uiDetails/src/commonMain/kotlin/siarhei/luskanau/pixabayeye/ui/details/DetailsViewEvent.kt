package siarhei.luskanau.pixabayeye.ui.details

sealed interface DetailsViewEvent {
    data object Launched : DetailsViewEvent
    data object NavigateBack : DetailsViewEvent
}
