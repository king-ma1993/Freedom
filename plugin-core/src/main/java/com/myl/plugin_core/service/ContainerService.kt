package com.myl.plugin_core.service

import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

@Deprecated("废弃")
class ContainerService : Service(), ServiceSuperMethodInterface {

    private var pluginServiceMethodInterface: PluginServiceMethodInterface? = null

    override fun superStartForeground(id: Int, notification: Notification) {
        super.startForeground(id, notification)
    }

    override fun superStopForeground(removeNotification: Boolean) {
        super.stopForeground(removeNotification)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun superStopForeground(flags: Int) {
        super.stopForeground(flags)
    }

    override fun superStopSelf() {
        super.stopSelf()
    }

    override fun superStopSelf(startId: Int) {
        super.stopSelf(startId)
    }

    override fun superStopSelfResult(startId: Int): Boolean {
        return super.stopSelfResult(startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return pluginServiceMethodInterface?.onBind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val serviceAction = intent?.getIntExtra(ORIGIN_SERVICE_ACTION, INVALID_SERVICE_ACTION)
//        val pluginServiceIntent: Intent? = intent?.getParcelableExtra(ORIGIN_SERVICE_INTENT)
//        if (serviceAction == INVALID_SERVICE_ACTION || pluginServiceIntent == null) {
//            return START_STICKY
//        }
//        val delegateProvider =
//            DelegateProviderFactory.getDelegateProvider(DelegateProviderFactory.getDelegateProviderKey())
//
//        when (serviceAction) {
//            START_SERVICE -> {
//                delegateProvider?.getPluginServiceMethodInterface(
//                    pluginServiceIntent?.component
//                )?.apply {
//                        pluginServiceMethodInterface = this
//                        pluginServiceMethodInterface?.setServiceSuperMethodInterface(this@ContainerService)
//                    }
//
//                pluginServiceMethodInterface?.onStartCommand(
//                    pluginServiceIntent,
//                    flags,
//                    startId
//                )
//            }
//
//
//            STOP_SERVICE -> delegateProvider?.destroyPluginServiceMethodInterface(
//                pluginServiceIntent?.component
//            )
//
//        }
        return START_STICKY
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        return super.startForegroundService(service)
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        pluginServiceMethodInterface?.onStart(intent, startId)
    }


    override fun onDestroy() {
        pluginServiceMethodInterface?.onDestroy()
    }


}