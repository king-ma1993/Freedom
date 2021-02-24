package com.myl.plugin_core.manager.server

import android.content.ComponentName
import android.content.Context
import android.os.Parcel
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.DESCRIPTOR
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_convertPluginIntent
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_convertServiceIntent
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findPluginActivityInfo
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findPluginServiceInfo
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_findReceiverIntentFilter
import com.myl.plugin_core.manager.client.PMBinderClientInterface.Companion.TRANSACTION_loadPlugin

class PluginManagerBinder(val context: Context) : android.os.Binder() {


    private val pluginServerManager: PluginServerManager = PluginServerManager(context)


    override
    fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        return when (code) {
            TRANSACTION_loadPlugin -> {
                data.enforceInterface(DESCRIPTOR)
                val pluginPath: String = data.readString() ?: ""
                val pluginServerInfo = pluginServerManager.loadPlugin(pluginPath)
                reply!!.writeNoException()
                if (pluginServerInfo != null) {
                    reply.writeInt(1)
                    pluginServerInfo.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                true
            }
            TRANSACTION_convertPluginIntent -> {
                data.enforceInterface(DESCRIPTOR)
                val _arg0: android.content.Intent? = if (0 != data.readInt()) {
                    android.content.Intent.CREATOR.createFromParcel(data)
                } else {
                    null
                }
                val convertIntent = pluginServerManager.convertPluginIntent(_arg0!!)
                reply!!.writeNoException()
                if (convertIntent != null) {
                    reply.writeInt(1)
                    convertIntent.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                return true
            }
            TRANSACTION_findReceiverIntentFilter -> {
                data.enforceInterface(DESCRIPTOR)
                val receiverClassName: String = data.readString() ?: ""
                val pluginPkgName: String = data.readString() ?: ""
                val pluginReceiverInfo =
                    pluginServerManager.findPluginReceiverInfo(receiverClassName, pluginPkgName)
                reply!!.writeNoException()
                if (pluginReceiverInfo != null) {
                    reply.writeInt(1)
                    pluginReceiverInfo.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                return true
            }
            TRANSACTION_findPluginActivityInfo -> {
                data.enforceInterface(DESCRIPTOR)
                val _arg0: ComponentName? = if (0 != data.readInt()) {
                    ComponentName.CREATOR.createFromParcel(data)
                } else {
                    null
                }
                val activityInfo = pluginServerManager.findPluginActivityInfo(_arg0)
                reply!!.writeNoException()
                if (activityInfo != null) {
                    reply.writeInt(1)
                    activityInfo.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                return true
            }
            TRANSACTION_findPluginServiceInfo -> {
                data.enforceInterface(DESCRIPTOR)
                val _arg0: ComponentName? = if (0 != data.readInt()) {
                    ComponentName.CREATOR.createFromParcel(data)
                } else {
                    null
                }
                val serviceInfo = pluginServerManager.findPluginServiceInfo(_arg0)
                reply!!.writeNoException()
                if (serviceInfo != null) {
                    reply.writeInt(1)
                    serviceInfo.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                return true
            }
            TRANSACTION_convertServiceIntent -> {
                data.enforceInterface(DESCRIPTOR)
                val _arg0: android.content.Intent? = if (0 != data.readInt()) {
                    android.content.Intent.CREATOR.createFromParcel(data)
                } else {
                    null
                }
                val convertIntent = pluginServerManager.convertServiceIntent(_arg0!!)
                reply!!.writeNoException()
                if (convertIntent != null) {
                    reply.writeInt(1)
                    convertIntent.writeToParcel(
                        reply,
                        android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE
                    )
                } else {
                    reply.writeInt(0)
                }
                return true
            }
            else -> false
        }
    }

}