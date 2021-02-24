package com.myl.plugin_core.manager.client

import android.content.ComponentName
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ServiceInfo
import android.os.IBinder
import com.myl.plugin_core.manager.server.PluginReceiverInfo
import com.myl.plugin_core.manager.server.PluginServerInfo


interface PMBinderClientInterface {

    companion object {
        const val DESCRIPTOR = "PMBinderClientInterface"
        const val TRANSACTION_loadPlugin = IBinder.FIRST_CALL_TRANSACTION + 1
        const val TRANSACTION_convertPluginIntent = IBinder.FIRST_CALL_TRANSACTION + 2
        const val TRANSACTION_findReceiverIntentFilter = IBinder.FIRST_CALL_TRANSACTION + 3
        const val TRANSACTION_findPluginActivityInfo = IBinder.FIRST_CALL_TRANSACTION + 4
        const val TRANSACTION_findPluginServiceInfo = IBinder.FIRST_CALL_TRANSACTION + 5
        const val TRANSACTION_convertServiceIntent = IBinder.FIRST_CALL_TRANSACTION + 6
    }

    fun loadPlugin(pluginPath: String?): PluginServerInfo?

    fun convertPluginIntent(intent: Intent): Intent?

    fun findPluginReceiverInfo(
        receiverClassName: String,
        pluginName: String
    ): PluginReceiverInfo?

    fun findPluginActivityInfo(componentName: ComponentName?): ActivityInfo?

    fun findPluginServiceInfo(componentName: ComponentName?): ServiceInfo?

    fun convertServiceIntent(service: Intent?): Intent?
}