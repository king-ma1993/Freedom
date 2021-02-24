package com.myl.plugin_core.manager.client

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.myl.plugin_core.manager.server.PluginManagerBinder

class PluginManagerService : Service() {

    private var pluginManagerBinder: PluginManagerBinder? = null


    override fun onCreate() {
        super.onCreate()
        pluginManagerBinder = PluginManagerBinder(applicationContext)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return pluginManagerBinder
    }
}