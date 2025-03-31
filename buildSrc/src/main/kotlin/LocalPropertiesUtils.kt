import java.util.Properties

fun isDebugScreenEnabled(properties: () -> Properties) = (
    System.getProperty("IS_DEBUG_SCREEN_ENABLED")
        ?: properties().getProperty("IS_DEBUG_SCREEN_ENABLED")
    ).toBoolean()
