package siarhei.luskanau.pixabayeye.core.network.ktor

import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

private const val SINGLE_HIT_JSON = """
{
  "total": 1,
  "totalHits": 1,
  "hits": [
    {
      "id": 1,
      "pageURL": "https://pixabay.com/1",
      "type": "photo",
      "tags": "cat, animal",
      "views": 100,
      "downloads": 10,
      "likes": 5,
      "comments": 1,
      "user_id": 42,
      "user": "someone",
      "userImageURL": "https://pixabay.com/user.jpg",
      "noAiTraining": false,
      "isAiGenerated": false,
      "isGRated": true,
      "isLowQuality": false,
      "userURL": "https://pixabay.com/users/someone"
    }
  ]
}
"""

class PixabayApiClientCommonTest {

    @Test
    fun isApiKeyOk_returnsTrue_onHttpOk() = kotlinx.coroutines.runBlocking {
        val client = PixabayApiClient(
            httpClient = mockHttpClient { request ->
                assertTrue(request.url.parameters["key"] == "test-api-key")
                respond(
                    content = "",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            },
            prefService = FakePrefService()
        )

        assertTrue(client.isApiKeyOk(apiKey = "test-api-key"))
    }

    @Test
    fun isApiKeyOk_throws_onHttpError() = kotlinx.coroutines.runBlocking {
        val client = PixabayApiClient(
            httpClient = mockHttpClient { request ->
                respond(
                    content = "",
                    status = HttpStatusCode.Unauthorized,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            },
            prefService = FakePrefService()
        )

        assertFailsWith<Error> { client.isApiKeyOk(apiKey = "invalid") }
        Unit
    }

    @Test
    fun getImages_parsesResponse_andAppliesApiKeyFromPrefService() =
        kotlinx.coroutines.runBlocking {
            var capturedKey: String? = null
            val client = PixabayApiClient(
                httpClient = mockHttpClient { request ->
                    capturedKey = request.url.parameters["key"]
                    respond(
                        content = SINGLE_HIT_JSON,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                },
                prefService = FakePrefService(apiKey = "key-from-prefs")
            )

            val result = client.getImages(query = "cats", perPage = 20, page = 1)

            assertEquals("key-from-prefs", capturedKey)
            assertEquals(1, result.hits.size)
            assertEquals(1L, result.hits.first().id)
        }
}
