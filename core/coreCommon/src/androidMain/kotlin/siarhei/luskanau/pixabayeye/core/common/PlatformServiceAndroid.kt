package siarhei.luskanau.pixabayeye.core.common

import android.os.StrictMode

internal class PlatformServiceAndroid : PlatformService {

    override fun setStrictMode(isEnabled: Boolean) {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                // .detectAll() for all detectable problems
                .penaltyLog()
                .also {
                    if (isEnabled) {
                        it.penaltyDeath()
                    }
                }
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .also {
                    if (isEnabled) {
                        it.penaltyDeath()
                    }
                }
                .build()
        )
    }
}
