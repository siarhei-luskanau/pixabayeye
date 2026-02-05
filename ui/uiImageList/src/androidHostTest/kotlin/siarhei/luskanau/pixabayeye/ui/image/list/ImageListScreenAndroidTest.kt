package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class ImageListScreenAndroidTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() {
        composeRule.setContent { ImageListContentRefreshIsLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsLoading() {
        composeRule.setContent { ImageListContentRefreshIsLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() {
        composeRule.setContent { ImageListContentRefreshIsErrorPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsError() {
        composeRule.setContent { ImageListContentRefreshIsErrorPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() {
        composeRule.setContent {
            ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList)
        }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataPresentAndNotLoading() {
        composeRule.setContent {
            ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList)
        }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() {
        composeRule.setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataAbsentAndNotLoading() {
        composeRule.setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() {
        composeRule.setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependLoading() {
        composeRule.setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() {
        composeRule.setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependError() {
        composeRule.setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() {
        composeRule.setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendLoading() {
        composeRule.setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() {
        composeRule.setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendError() {
        composeRule.setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }
}
