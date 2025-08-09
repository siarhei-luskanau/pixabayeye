package siarhei.luskanau.pixabayeye.core.network.stub

import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@Single
internal class StubPixabayApiService : PixabayApiService {

    override suspend fun isApiKeyOk(apiKey: String?): NetworkResult<Boolean> =
        NetworkResult.Success(true)

    override suspend fun getImages(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> {
        val pageSize = perPage ?: 20
        val images = HIT_LIST
            .filter { TYPES_IMAGE.contains(it.type) }
            .drop(((page ?: 0) - 1) * pageSize)
            .take(pageSize)
        return NetworkResult.Success(images)
    }

    override suspend fun getImage(imageId: Long): NetworkResult<HitModel> {
        val image = HIT_LIST.first { it.id == imageId }
        return NetworkResult.Success(image)
    }

    override suspend fun getVideos(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> {
        val pageSize = perPage ?: 20
        val videos = HIT_LIST
            .filter { TYPES_VIDEO.contains(it.type) }
            .drop(((page ?: 0) - 1) * pageSize)
            .take(pageSize)
        return NetworkResult.Success(videos)
    }

    override suspend fun getVideo(videoId: Long): NetworkResult<HitModel> {
        val video = HIT_LIST.first { it.id == videoId }
        return NetworkResult.Success(video)
    }
}
