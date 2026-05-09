package siarhei.luskanau.pixabayeye.core.common

import kotlinx.coroutines.Dispatchers

internal class DispatcherSetWeb : DispatcherSet {
    override fun defaultDispatcher() = Dispatchers.Default
    override fun ioDispatcher() = Dispatchers.Default
    override fun mainDispatcher() = Dispatchers.Main
}
