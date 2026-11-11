package com.agentsanywhere.app.feature.terminal

import android.content.ComponentName
import android.content.ContextWrapper
import android.content.Intent
import org.robolectric.RuntimeEnvironment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class RemoteTerminalForegroundServiceTest {
    private class RecordingContext : ContextWrapper(RuntimeEnvironment.getApplication()) {
        var method: String? = null
        override fun startService(service: Intent): ComponentName? {
            method = "startService"
            return service.component
        }
        override fun startForegroundService(service: Intent): ComponentName? {
            method = "startForegroundService"
            return service.component
        }
    }

    @Test
    @Config(sdk = [24, 25])
    fun startsLegacyServiceOnAndroid7() {
        val context = RecordingContext()
        RemoteTerminalForegroundService.start(context)
        assertEquals("startService", context.method)
    }

    @Test
    @Config(sdk = [26])
    fun startsForegroundServiceOnAndroid8() {
        val context = RecordingContext()
        RemoteTerminalForegroundService.start(context)
        assertEquals("startForegroundService", context.method)
    }
}
