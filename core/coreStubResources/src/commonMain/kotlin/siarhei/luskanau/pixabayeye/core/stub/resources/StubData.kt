package siarhei.luskanau.pixabayeye.core.stub.resources

import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.network.api.ImageHitModel
import siarhei.luskanau.pixabayeye.core.network.api.VideoHitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.resources.Res

val TYPES_IMAGE = listOf("photo", "illustration")
val TYPES_VIDEO = listOf("video", "animation")
val HIT_LIST = listOf(
    HitModel(
        id = 9767930,
        pageURL = "https://pixabay.com/photos/milky-way-universe-stars-night-sky-9767930/",
        type = "photo",
        tags =
        "milky way, universe, stars, night sky, galaxy, heaven, cosmos, starry sky, " +
            "star park, winklmoosalm, chiemgau alps, upper bavaria",
        views = 8210,
        downloads = 7562,
        likes = 81,
        comments = 28,
        userId = 1425977,
        userName = "ChiemSeherin",
        userImageURL = Res.getUri("drawable/16-01-20-793_250x250.jpeg"),
        noAiTraining = false,
        isAiGenerated = false,
        isGRated = true,
        isLowQuality = false,
        userURL = "https://pixabay.com/users/1425977/",
        imageModel = ImageHitModel(
            previewUrl = Res.getUri("drawable/milky-way-9767930_150.jpg"),
            previewWidth = 150,
            previewHeight = 100,
            webFormatUrl = Res.getUri("drawable/milky-way-g7c0b40e73_640.jpg"),
            webFormatWidth = 640,
            webFormatHeight = 427,
            largeImageUrl = Res.getUri("drawable/milky-way-gbdf0d2604_1280.jpg"),
            imageWidth = 7746,
            imageHeight = 5167,
            imageSize = 15804561,
            collections = 23
        ),
        duration = null,
        videosModel = null
    ),
    HitModel(
        id = 8359510,
        pageURL = "https://pixabay.com/illustrations/ai-generated-cat-feline-face-8359510/",
        type = "illustration",
        tags =
        "ai generated, cat, feline, face, beauty, towel, bathrobe, care, bath, spa, hat, " +
            "clean",
        views = 10274,
        downloads = 7127,
        likes = 101,
        comments = 0,
        userId = 23122350,
        userName = "khademul55772",
        userImageURL = Res.getUri("drawable/00-27-39-308_250x250.jpg"),
        noAiTraining = false,
        isAiGenerated = true,
        isGRated = true,
        isLowQuality = false,
        userURL = "https://pixabay.com/users/23122350/",
        imageModel = ImageHitModel(
            previewUrl = Res.getUri("drawable/ai-generated-8359510_150.jpg"),
            previewWidth = 150,
            previewHeight = 150,
            webFormatUrl = Res.getUri("drawable/ai-generated-g1d876b320_640.jpg"),
            webFormatWidth = 640,
            webFormatHeight = 640,
            largeImageUrl = Res.getUri("drawable/ai-generated-gaae6db58e_1280.jpg"),
            imageWidth = 4267,
            imageHeight = 4267,
            imageSize = 1979378,
            collections = 76
        ),
        duration = null,
        videosModel = null
    ),
    HitModel(
        id = 186714,
        pageURL = "https://pixabay.com/videos/id-186714/",
        type = "animation",
        tags = "ai generated, sunset, sky, clouds, fields, nature",
        views = 432248,
        downloads = 275455,
        likes = 1997,
        comments = 292,
        userId = 40312779,
        userName = "u_a5gxzma1ko",
        userImageURL = "",
        noAiTraining = false,
        isAiGenerated = true,
        isGRated = true,
        isLowQuality = false,
        userURL = "https://pixabay.com/users/40312779/",
        imageModel = null,
        duration = 7,
        videosModel = mapOf(
            "large" to VideoHitModel(
                url = Res.getUri("drawable/186714-878826932_large.mp4"),
                width = 1080,
                height = 1920,
                size = 3341828,
                thumbnail = Res.getUri("drawable/186714-878826932_large.jpg")
            ),
            "medium" to VideoHitModel(
                url = Res.getUri("drawable/186714-878826932_medium.mp4"),
                width = 720,
                height = 1280,
                size = 2006701,
                thumbnail = Res.getUri("drawable/186714-878826932_medium.jpg")
            ),
            "small" to VideoHitModel(
                url = Res.getUri("drawable/186714-878826932_small.mp4"),
                width = 540,
                height = 960,
                size = 1127507,
                thumbnail = Res.getUri("drawable/186714-878826932_small.jpg")
            ),
            "tiny" to VideoHitModel(
                url = Res.getUri("drawable/186714-878826932_tiny.mp4"),
                width = 360,
                height = 640,
                size = 515814,
                thumbnail = Res.getUri("drawable/186714-878826932_tiny.jpg")
            )
        )
    )

//            HitModel(
//                id = ,
//                pageURL = ,
//                type = ,
//                tags = ,
//                views = ,
//                downloads = ,
//                likes = ,
//                comments = ,
//                userId = ,
//                userName = ,
//                userImageURL = Res.getUri("drawable/"),
//                noAiTraining = ,
//                isAiGenerated = ,
//                isGRated = ,
//                isLowQuality = ,
//                userURL = ,
//                imageModel = ImageHitModel(
//                    previewUrl = Res.getUri("drawable/"),
//                    previewWidth = ,
//                    previewHeight = ,
//                    webFormatUrl = Res.getUri("drawable/"),
//                    webFormatWidth = ,
//                    webFormatHeight = ,
//                    largeImageUrl = Res.getUri("drawable/"),
//                    imageWidth = ,
//                    imageHeight = ,
//                    imageSize = ,
//                    collections =
//                ),
//                duration = ,
//                videosModel = mapOf(
//                    "large" to VideoHitModel(
//                        url = Res.getUri("drawable/"),
//                        width = ,
//                        height = ,
//                        size = ,
//                        thumbnail = Res.getUri("drawable/")
//                    ),
//                    "medium" to VideoHitModel(
//                        url = Res.getUri("drawable/"),
//                        width = ,
//                        height = ,
//                        size = ,
//                        thumbnail = Res.getUri("drawable/")
//                    ),
//                    "small" to VideoHitModel(
//                        url = Res.getUri("drawable/"),
//                        width = ,
//                        height = ,
//                        size = ,
//                        thumbnail = Res.getUri("drawable/")
//                    ),
//                    "tiny" to VideoHitModel(
//                        url = Res.getUri("drawable/"),
//                        width = ,
//                        height = ,
//                        size = ,
//                        thumbnail = Res.getUri("drawable/")
//                    )
//                )
//            )
)
