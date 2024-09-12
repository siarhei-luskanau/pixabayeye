package siarhei.luskanau.pixabayeye.core.common

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Single

@Single
internal class DispatcherSetAndroid : DispatcherSet {
    override fun ioDispatcher() = Dispatchers.IO
    override fun mainDispatcher() = Dispatchers.Main
    override fun <T> runBlocking(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T = kotlinx.coroutines.runBlocking(
        context = context,
        block = block
    )
}
