package siarhei.luskanau.pixabayeye.core.common

import org.koin.core.annotation.Single

@Single
internal class PlatformServiceJs : PlatformService {
    override fun setStrictMode(isEnabled: Boolean) = Unit
}
