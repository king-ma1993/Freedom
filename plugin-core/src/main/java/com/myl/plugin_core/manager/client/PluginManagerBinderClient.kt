package com.myl.plugin_core.manager.client

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.os.Parcel
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.DESCRIPTOR
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_convertPluginIntent
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_convertServiceIntent
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findPluginActivityInfo
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findPluginServiceInfo
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findReceiverIntentFilter
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_loadPlugin
import com.myl.plugin_core.manager.server.PluginReceiverInfo
import com.myl.plugin_core.manager.server.PluginServerInfo

class PluginManagerBinderClient(private val mRemote: IBinder) : PMBinderClientInterface {

    override fun findPluginServiceInfo(componentName: ComponentName?): ServiceInfo? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var serverInfo: ServiceInfo? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            if (componentName != null) {
                _data.writeInt(1)
                componentName.writeToParcel(_data, 0)
            } else {
                _data.writeInt(0)
            }
            mRemote.transact(TRANSACTION_findPluginServiceInfo, _data, _reply, 0)
            _reply.readException()
            serverInfo = if (0 != _reply.readInt()) {
                ServiceInfo.CREATOR.createFromParcel(_reply)
            } else {
                null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return serverInfo
    }

    override fun findPluginActivityInfo(componentName: ComponentName?): ActivityInfo? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var activityInfo: ActivityInfo? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            if (componentName != null) {
                _data.writeInt(1)
                componentName.writeToParcel(_data, 0)
            } else {
                _data.writeInt(0)
            }
            mRemote.transact(TRANSACTION_findPluginActivityInfo, _data, _reply, 0)
            _reply.readException()
            activityInfo = if (0 != _reply.readInt()) {
                ActivityInfo.CREATOR.createFromParcel(_reply)
            } else {
                null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return activityInfo
    }

    override fun findPluginReceiverInfo(
        receiverClassName: String,
        pluginPkgName: String
    ): PluginReceiverInfo? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var pluginReceiverInfo: PluginReceiverInfo? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            _data.writeString(receiverClassName)
            _data.writeString(pluginPkgName)
            mRemote.transact(TRANSACTION_findReceiverIntentFilter, _data, _reply, 0)
            _reply.readException()
            if (0 != _reply.readInt()) {
                pluginReceiverInfo = PluginReceiverInfo.createFromParcel(_reply)
            } else {
                pluginReceiverInfo = null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return pluginReceiverInfo
    }

    override fun convertPluginIntent(intent: Intent): Intent? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var convertIntent: Intent? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            if (intent != null) {
                _data.writeInt(1)
                intent.writeToParcel(_data, 0)
            } else {
                _data.writeInt(0)
            }
            mRemote.transact(TRANSACTION_convertPluginIntent, _data, _reply, 0)
            _reply.readException()
            if (0 != _reply.readInt()) {
                convertIntent = Intent.CREATOR.createFromParcel(_reply)
            } else {
                convertIntent = null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return convertIntent
    }


    override fun loadPlugin(pluginPath: String?): PluginServerInfo? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var pluginServerInfo: PluginServerInfo? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            _data.writeString(pluginPath)
            mRemote.transact(TRANSACTION_loadPlugin, _data, _reply, 0)
            _reply.readException()
            pluginServerInfo = if (0 != _reply.readInt()) {
                PluginServerInfo.createFromParcel(_reply)
            } else {
                null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return pluginServerInfo
    }

    override fun convertServiceIntent(service: Intent?): Intent? {
        val _data = Parcel.obtain()
        val _reply = Parcel.obtain()
        var convertIntent: Intent? = null
        try {
            _data.writeInterfaceToken(DESCRIPTOR)
            if (service != null) {
                _data.writeInt(1)
                service.writeToParcel(_data, 0)
            } else {
                _data.writeInt(0)
            }
            mRemote.transact(TRANSACTION_convertServiceIntent, _data, _reply, 0)
            _reply.readException()
            if (0 != _reply.readInt()) {
                convertIntent = Intent.CREATOR.createFromParcel(_reply)
            } else {
                convertIntent = null
            }
        } finally {
            _reply.recycle()
            _data.recycle()
        }
        return convertIntent
    }
}