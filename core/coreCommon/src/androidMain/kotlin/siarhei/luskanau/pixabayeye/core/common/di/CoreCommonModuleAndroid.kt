package siarhei.luskanau.pixabayeye.core.common.di

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet

actual val coreCommonPlatformModule =
    module {
        single<DispatcherSet> {
            object : DispatcherSet {
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
        }
    }
