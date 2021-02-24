package com.myl.plugin_core.service

import android.app.Notification
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder

@Deprecated("废弃")
interface PluginServiceMethodInterface {

    fun setServiceSuperMethodInterface(serviceSuperMethodInterface: ServiceSuperMethodInterface)

    fun onBind(intent: Intent?): IBinder?

    fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int

    fun onDestroy()

    fun onConfigurationChanged(newConfig: Configuration)

    fun onLowMemory()

    fun onTrimMemory(level: Int)

    fun onUnbind(intent: Intent): Boolean

    fun onTaskRemoved(rootIntent: Intent)

    fun onCreate()

    fun onRebind(intent: Intent)

    fun onStart(intent: Intent?, startId: Int)

    fun setForeground(isForeground: Boolean)

    fun startForeground(id: Int, notification: Notification)

    fun stopForeground(removeNotification: Boolean)

    fun stopForeground(flags: Int)

    fun stopSelf()

    fun stopSelf(startId: Int)

    fun stopSelfResult(startId: Int): Boolean

}