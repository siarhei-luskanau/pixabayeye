package siarhei.luskanau.pixabayeye.core.common

import kotlinx.coroutines.Dispatchers

internal class DispatcherSetAndroid : DispatcherSet {
    override fun defaultDispatcher() = Dispatchers.Default
    override fun ioDispatcher() = Dispatchers.IO
    override fun mainDispatcher() = Dispatchers.Main
}
