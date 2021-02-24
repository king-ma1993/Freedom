package com.myl.androidapkplugin.pluginlib.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.androidapkplugin.pluginlib.plugin.PluginContext
import com.myl.plugin_core.service.PluginServiceMethodInterface
import com.myl.plugin_core.service.ServiceSuperMethodInterface

open class PluginService : PluginContext(),
    PluginServiceMethodInterface {

    fun setHostContextAsBase(context: Context) {
        attachBaseContext(context)
    }

    override fun setServiceSuperMethodInterface(serviceSuperMethodInterface: ServiceSuperMethodInterface) {
        serviceMethodInterface = serviceSuperMethodInterface
    }

    private var serviceMethodInterface: ServiceSuperMethodInterface? = null


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {

    }

    override fun onLowMemory() {

    }

    override fun onTrimMemory(level: Int) {

    }

    override fun onUnbind(intent: Intent): Boolean {
        return false
    }

    override fun onTaskRemoved(rootIntent: Intent) {

    }

    override fun onCreate() {

    }

    override fun onRebind(intent: Intent) {

    }

    override fun onStart(intent: Intent?, startId: Int) {
    }

    override fun setForeground(isForeground: Boolean) {
    }

    override fun startForeground(id: Int, notification: Notification) {
        serviceMethodInterface?.superStartForeground(id, notification)
    }

    override fun stopForeground(removeNotification: Boolean) {
        serviceMethodInterface?.superStopForeground(removeNotification)
    }

    override fun stopForeground(flags: Int) {
        serviceMethodInterface?.superStopForeground(flags)
    }

    override fun stopSelf() {
        serviceMethodInterface?.superStopSelf()
    }


    override fun stopSelf(startId: Int) {
        serviceMethodInterface?.superStopSelf(startId)
    }

    /**
     * 插件环境下Service不支持调用带参数的stopSelf
     */
    override fun stopSelfResult(startId: Int): Boolean {
        return serviceMethodInterface?.superStopSelfResult(startId) ?: true
    }

    fun getApplication(): PluginApplication? {
        return pluginApplication
    }
}