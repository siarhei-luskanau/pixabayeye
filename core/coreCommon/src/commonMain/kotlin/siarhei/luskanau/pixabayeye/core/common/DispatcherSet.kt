package siarhei.luskanau.pixabayeye.core.common

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface DispatcherSet {

    fun defaultDispatcher(): CoroutineDispatcher

    fun ioDispatcher(): CoroutineDispatcher

    fun mainDispatcher(): CoroutineDispatcher

    fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T
}
