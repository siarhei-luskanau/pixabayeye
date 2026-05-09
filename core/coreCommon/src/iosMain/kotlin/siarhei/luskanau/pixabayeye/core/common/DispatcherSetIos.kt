package siarhei.luskanau.pixabayeye.core.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class DispatcherSetIos : DispatcherSet {
    override fun defaultDispatcher() = Dispatchers.Default
    override fun ioDispatcher() = Dispatchers.IO
    override fun mainDispatcher() = Dispatchers.Main
}
