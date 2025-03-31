package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import sp.bvantur.inspektify.ktor.InspektifyKtor

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.configureHttpClientDebug() {
    install(Logging) {
        logger = Logger.SIMPLE
        level = io.ktor.client.plugins.logging.LogLevel.ALL
    }
    install(InspektifyKtor) {
        logLevel = sp.bvantur.inspektify.ktor.LogLevel.All
    }
}
