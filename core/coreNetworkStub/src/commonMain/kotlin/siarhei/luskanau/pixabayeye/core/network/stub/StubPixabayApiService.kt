package siarhei.luskanau.pixabayeye.core.network.stub

import org.koin.core.annotation.Single
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.ImageHitModel
import siarhei.luskanau.pixabayeye.core.network.api.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.api.VideoHitModel

@Single
@Suppress("ktlint:standard:max-line-length")
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
            .filter { it.type == "photo" }
            .drop((page ?: 0) * pageSize)
            .take(pageSize)
        return NetworkResult.Success(images)
    }

    override suspend fun getImage(imageId: Long): NetworkResult<HitModel> {
        val image = HIT_LIST.first { it.type == "photo" }.copy(id = imageId)
        return NetworkResult.Success(image)
    }

    override suspend fun getVideos(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> {
        val pageSize = perPage ?: 20
        val videos = HIT_LIST
            .filter { it.type == "video" }
            .drop((page ?: 0) * pageSize)
            .take(pageSize)
        return NetworkResult.Success(videos)
    }

    override suspend fun getVideo(videoId: Long): NetworkResult<HitModel> {
        val video = HIT_LIST.first { it.type == "video" }.copy(id = videoId)
        return NetworkResult.Success(video)
    }

    companion object {
        private val HIT_LIST = listOf<HitModel>(
            // Res.getUri("drawable/${key}.$s")
            HitModel(
                id = 0,
                pageURL = "https://pixabay.com/photos/nature-landscape-mountain-/",
                type = "photo",
                tags = "nature, landscape, beautiful, mountain, sunset, forest, trees",
                views = 25000,
                downloads = 1500,
                likes = 750,
                comments = 120,
                userId = 12345L,
                userName = "NaturePhotographer",
                userImageURL = "https://cdn.pixabay.com/user/2023/01/01/01-01-01-12345.jpg",
                noAiTraining = false,
                isAiGenerated = false,
                isGRated = true,
                userURL = "https://pixabay.com/users/naturephotographer/",
                imageModel = ImageHitModel(
                    previewUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature--150_150.jpg",
                    previewWidth = 150,
                    previewHeight = 150,
                    middleImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature--640.jpg",
                    middleImageWidth = 640,
                    middleImageHeight = 480,
                    largeImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature--1920.jpg",
                    imageWidth = 1920,
                    imageHeight = 1440,
                    imageSize = 1500000,
                    collections = 25
                ),
                duration = null,
                videosModel = null
            ),
            HitModel(
                id = 0,
                pageURL = "https://pixabay.com/videos/nature-landscape-mountain-/",
                type = "video",
                tags = "nature, landscape, beautiful, mountain, sunset, forest, trees, wildlife",
                views = 45000,
                downloads = 2200,
                likes = 1200,
                comments = 180,
                userId = 67890L,
                userName = "NatureVideographer",
                userImageURL = "https://cdn.pixabay.com/user/2023/01/01/01-01-01-67890.jpg",
                noAiTraining = false,
                isAiGenerated = false,
                isGRated = true,
                userURL = "https://pixabay.com/users/naturevideographer/",
                imageModel = null,
                duration = 45,
                videosModel = mapOf(
                    "large" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-.mp4?width=1280&hash=abc123",
                        width = 1280,
                        height = 720,
                        size = 12000000,
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-.jpg?width=1280"
                    ),
                    "medium" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-.mp4?width=640&hash=def456",
                        width = 640,
                        height = 360,
                        size = 6000000,
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-.jpg?width=640"
                    ),
                    "small" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-.mp4?width=320&hash=ghi789",
                        width = 320,
                        height = 180,
                        size = 3000000,
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-.jpg?width=320"
                    )
                )
            )
        )
    }
}
