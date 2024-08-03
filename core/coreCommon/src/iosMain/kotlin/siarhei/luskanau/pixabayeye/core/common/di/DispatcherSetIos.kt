package siarhei.luskanau.pixabayeye.core.common.di

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet

@Single
internal class DispatcherSetIos : DispatcherSet {
    override fun ioDispatcher() = Dispatchers.Default
    override fun mainDispatcher() = Dispatchers.Main
    override fun <T> runBlocking(
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): T = kotlinx.coroutines.runBlocking(
        context = context,
        block = block
    )
}
