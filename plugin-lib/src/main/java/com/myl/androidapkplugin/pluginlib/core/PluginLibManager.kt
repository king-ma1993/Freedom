package com.myl.androidapkplugin.pluginlib.core

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.content.pm.ServiceInfo
import android.os.IBinder
import androidx.collection.ArrayMap
import com.myl.androidapkplugin.pluginlib.proxy.PluginPackageManager
import com.myl.androidapkplugin.pluginlib.proxy.PluginPackageManagerImpl
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.manager.client.PluginManagerBinderClient
import com.myl.plugin_core.manager.client.PluginManagerService
import com.myl.plugin_core.manager.server.PluginReceiverInfo
import com.myl.plugin_core.util.LogUtils
import java.util.concurrent.atomic.AtomicBoolean

object PluginLibManager {

    private const val TAG = "PluginLibManager"

    /**
     * 防止绑定service重入
     */
    private val mServiceConnected = AtomicBoolean(false)
    private var iPluginManagerBinder: PluginManagerBinderClient? = null

    /**
     * 存放插件activity的信息
     */
    private val mCacheActivitiesInfo: MutableMap<ComponentName, ActivityInfo> = ArrayMap()
    private val mCachePluginReceiverInfo: MutableMap<String, PluginReceiverInfo> = ArrayMap()

    /**
     * 存放插件service的信息
     */
    private val mCacheServiceInfo: MutableMap<ComponentName, ServiceInfo> = ArrayMap()
    private val mPackageManagerCache: MutableMap<ClassLoader, PluginPackageManagerImpl> = ArrayMap()

    fun bindPluginInfo(pluginInfo: PluginInfo) {
        val pluginPackageManagerImpl =
            PluginPackageManagerImpl(pluginInfo, pluginInfo.hostContext.packageManager)
        mPackageManagerCache[pluginInfo.mPluginClassLoader!!] = pluginPackageManagerImpl
    }

    fun getPluginPackageManager(classLoader: ClassLoader): PluginPackageManager? {
        return mPackageManagerCache[classLoader]
    }

    fun convertPluginActivityIntent(intent: Intent): Intent? {
        return iPluginManagerBinder?.convertPluginIntent(intent)
    }

    fun findPluginServiceInfo(serviceIntent: Intent?): ServiceInfo? {
        val componentName = serviceIntent?.component
        val cacheServiceInfo = mCacheServiceInfo[componentName]
        cacheServiceInfo?.apply {
            return this
        }
        val remoteServiceInfo = iPluginManagerBinder?.findPluginServiceInfo(componentName)
        remoteServiceInfo?.apply {
            mCacheServiceInfo[componentName!!] = this
        }
        return remoteServiceInfo
    }


    fun bindServerBinder(context: Context) {
        if (mServiceConnected.get()) {
            LogUtils.d(TAG, "bindServerBinder service is connecting")
            return
        }
        mServiceConnected.set(true)
        context?.apply {
            val intent = Intent(this, PluginManagerService::class.java)
            val isBind = bindService(intent, object : ServiceConnection {

                override fun onServiceDisconnected(name: ComponentName?) {
                    LogUtils.d(TAG, "bindServerBinder onServiceDisconnected")
                    mServiceConnected.set(false)
                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    LogUtils.d(TAG, "bindServerBinder onServiceConnected")
                    mServiceConnected.set(false)
                    service?.apply {
                        iPluginManagerBinder = getPluginManagerBinderClient(service)
                    }
                }


            }, Context.BIND_AUTO_CREATE)
            LogUtils.d(TAG, "bindServerBinder isSuccess:$isBind")
        }

    }

    private fun getPluginManagerBinderClient(iPluginManagerBinder: IBinder): PluginManagerBinderClient {
        return PluginManagerBinderClient(iPluginManagerBinder)
    }

    fun findPluginReceiverInfo(
        receiverClassName: String,
        pluginName: String
    ): PluginReceiverInfo? {
        val cachePluginReceiverInfo = mCachePluginReceiverInfo[receiverClassName]
        cachePluginReceiverInfo?.apply {
            return this
        }
        val remotePluginReceiverInfo =
            iPluginManagerBinder?.findPluginReceiverInfo(receiverClassName, pluginName)
        remotePluginReceiverInfo?.apply {
            mCachePluginReceiverInfo[receiverClassName!!] = this
        }
        return remotePluginReceiverInfo
    }


    fun findPluginActivityInfo(componentName: ComponentName?): ActivityInfo? {
        val cacheServiceInfo = mCacheActivitiesInfo[componentName]
        cacheServiceInfo?.apply {
            return this
        }
        val remoteServiceInfo = iPluginManagerBinder?.findPluginActivityInfo(componentName)
        remoteServiceInfo?.apply {
            mCacheActivitiesInfo[componentName!!] = this
        }
        return remoteServiceInfo
    }
}