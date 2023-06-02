package siarhei.luskanau.pixabayeye.ui.search

import app.cash.paging.Pager
import siarhei.luskanau.pixabayeye.network.HitModel

data class SearchVewState(
    val pager: Pager<Int, HitModel>,
)
