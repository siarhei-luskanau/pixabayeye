package siarhei.luskanau.pixabayeye.core.common

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

internal class DispatcherSetWeb : DispatcherSet {
    override fun defaultDispatcher() = Dispatchers.Default
    override fun ioDispatcher() = Dispatchers.Default
    override fun mainDispatcher() = Dispatchers.Main
    override fun <T> runBlocking(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T =
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.promise(
            context = context,
            block = block
        ).unsafeCast()
}
