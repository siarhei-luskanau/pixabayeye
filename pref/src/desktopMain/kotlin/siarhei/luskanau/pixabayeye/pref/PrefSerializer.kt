package siarhei.luskanau.pixabayeye.pref

import PixabayEye.pref.BuildConfig
import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal class PrefSerializer : OkioSerializer<PrefData> {

    private val parser = Json { ignoreUnknownKeys = true }

    override val defaultValue: PrefData
        get() = PrefData(pixabayApiKey = BuildConfig.PIXABAY_API_KEY)

    override suspend fun readFrom(source: BufferedSource): PrefData =
        parser.decodeFromString(
            PrefData.serializer(),
            source.readUtf8(),
        )

    override suspend fun writeTo(t: PrefData, sink: BufferedSink) {
        sink.writeUtf8(parser.encodeToString(PrefData.serializer(), t))
    }
}
