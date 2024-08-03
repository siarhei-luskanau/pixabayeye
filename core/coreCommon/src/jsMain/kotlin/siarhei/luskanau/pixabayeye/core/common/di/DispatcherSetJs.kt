package siarhei.luskanau.pixabayeye.core.common.di

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet

@Single
internal class DispatcherSetJs : DispatcherSet {
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
        ).unsafeCast<T>()
}
