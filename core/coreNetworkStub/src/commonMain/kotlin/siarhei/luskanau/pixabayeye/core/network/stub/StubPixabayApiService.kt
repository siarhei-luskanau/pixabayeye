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
        val images = createStubImages(query, perPage ?: 20)
        return NetworkResult.Success(images)
    }

    override suspend fun getImage(imageId: Long): NetworkResult<HitModel> {
        val image = createStubImage(imageId)
        return NetworkResult.Success(image)
    }

    override suspend fun getVideos(
        query: String?,
        perPage: Int?,
        page: Int?
    ): NetworkResult<List<HitModel>> {
        val videos = createStubVideos(query, perPage ?: 20)
        return NetworkResult.Success(videos)
    }

    override suspend fun getVideo(videoId: Long): NetworkResult<HitModel> {
        val video = createStubVideo(videoId)
        return NetworkResult.Success(video)
    }

    private fun createStubImages(query: String?, count: Int): List<HitModel> =
        (1..count).map { index ->
            HitModel(
                id = 1000L + index,
                pageURL = "https://pixabay.com/photos/nature-landscape-mountain-${1000 + index}/",
                type = "photo",
                tags =
                query?.let { "$it, nature, landscape, beautiful" }
                    ?: "nature, landscape, beautiful, mountain, sunset",
                views = (1000..50000).random(),
                downloads = (100..2000).random(),
                likes = (50..1000).random(),
                comments = (10..200).random(),
                userId = (1..1000).random().toLong(),
                userName = "Photographer$index",
                userImageURL = "https://cdn.pixabay.com/user/2023/01/01/01-01-01-$index.jpg",
                noAiTraining = false,
                isAiGenerated = false,
                isGRated = true,
                userURL = "https://pixabay.com/users/photographer$index/",
                imageModel = ImageHitModel(
                    previewUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/" +
                        "nature-${1000 + index}-150_150.jpg",
                    previewWidth = 150,
                    previewHeight = 150,
                    middleImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/" +
                        "nature-${1000 + index}-640.jpg",
                    middleImageWidth = 640,
                    middleImageHeight = 480,
                    largeImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/" +
                        "nature-${1000 + index}-1920.jpg",
                    imageWidth = 1920,
                    imageHeight = 1440,
                    imageSize = (500000..2000000).random(),
                    collections = (0..50).random()
                ),
                duration = null,
                videosModel = null
            )
        }

    private fun createStubImage(imageId: Long): HitModel = HitModel(
        id = imageId,
        pageURL = "https://pixabay.com/photos/nature-landscape-mountain-$imageId/",
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
            previewUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature-$imageId-150_150.jpg",
            previewWidth = 150,
            previewHeight = 150,
            middleImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature-$imageId-640.jpg",
            middleImageWidth = 640,
            middleImageHeight = 480,
            largeImageUrl = "https://cdn.pixabay.com/photo/2023/01/01/01/01/nature-$imageId-1920.jpg",
            imageWidth = 1920,
            imageHeight = 1440,
            imageSize = 1500000,
            collections = 25
        ),
        duration = null,
        videosModel = null
    )

    private fun createStubVideos(query: String?, count: Int): List<HitModel> =
        (1..count).map { index ->
            HitModel(
                id = 2000L + index,
                pageURL = "https://pixabay.com/videos/nature-landscape-mountain-${2000 + index}/",
                type = "video",
                tags =
                query?.let { "$it, nature, landscape, beautiful" }
                    ?: "nature, landscape, beautiful, mountain, sunset",
                views = (2000..80000).random(),
                downloads = (200..3000).random(),
                likes = (100..1500).random(),
                comments = (20..300).random(),
                userId = (1..1000).random().toLong(),
                userName = "Videographer$index",
                userImageURL = "https://cdn.pixabay.com/user/2023/01/01/01-01-01-$index.jpg",
                noAiTraining = false,
                isAiGenerated = false,
                isGRated = true,
                userURL = "https://pixabay.com/users/videographer$index/",
                imageModel = null,
                duration = (10..60).random(),
                videosModel = mapOf(
                    "large" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.mp4?width=1280&hash=abc123",
                        width = 1280,
                        height = 720,
                        size = (5000000..15000000).random(),
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.jpg?width=1280"
                    ),
                    "medium" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.mp4?width=640&hash=def456",
                        width = 640,
                        height = 360,
                        size = (2000000..8000000).random(),
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.jpg?width=640"
                    ),
                    "small" to VideoHitModel(
                        url = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.mp4?width=320&hash=ghi789",
                        width = 320,
                        height = 180,
                        size = (1000000..4000000).random(),
                        thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-${2000 + index}.jpg?width=320"
                    )
                )
            )
        }

    private fun createStubVideo(videoId: Long): HitModel = HitModel(
        id = videoId,
        pageURL = "https://pixabay.com/videos/nature-landscape-mountain-$videoId/",
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
                url = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.mp4?width=1280&hash=abc123",
                width = 1280,
                height = 720,
                size = 12000000,
                thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.jpg?width=1280"
            ),
            "medium" to VideoHitModel(
                url = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.mp4?width=640&hash=def456",
                width = 640,
                height = 360,
                size = 6000000,
                thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.jpg?width=640"
            ),
            "small" to VideoHitModel(
                url = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.mp4?width=320&hash=ghi789",
                width = 320,
                height = 180,
                size = 3000000,
                thumbnail = "https://cdn.pixabay.com/vimeo/3287147/nature-$videoId.jpg?width=320"
            )
        )
    )
}
