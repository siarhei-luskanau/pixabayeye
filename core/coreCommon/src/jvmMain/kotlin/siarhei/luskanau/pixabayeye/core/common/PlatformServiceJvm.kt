package siarhei.luskanau.pixabayeye.core.common

import org.koin.core.annotation.Single

@Single
internal class PlatformServiceJvm : PlatformService {
    override fun setStrictMode(isEnabled: Boolean) = Unit
}
