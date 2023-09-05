package siarhei.luskanau.pixabayeye.pref

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import pixabayeye.pref.BuildConfig

internal class PrefSerializer : OkioSerializer<PrefData> {

    private val parser = Json { ignoreUnknownKeys = true }

    override val defaultValue: PrefData
        get() = PrefData(pixabayApiKey = BuildConfig.PIXABAY_API_KEY)

    override suspend fun readFrom(source: BufferedSource): PrefData =
        try {
            parser.decodeFromString(
                PrefData.serializer(),
                source.readUtf8(),
            )
        } catch (error: Throwable) {
            defaultValue
        }

    override suspend fun writeTo(t: PrefData, sink: BufferedSink) {
        sink.writeUtf8(parser.encodeToString(PrefData.serializer(), t))
    }
}
