package siarhei.luskanau.pixabayeye.core.common

import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Single

@Single
internal class DispatcherSetJvm : DispatcherSet {
    override fun defaultDispatcher() = Dispatchers.Default
    override fun ioDispatcher() = Dispatchers.IO
    override fun mainDispatcher() = Dispatchers.Main
}
