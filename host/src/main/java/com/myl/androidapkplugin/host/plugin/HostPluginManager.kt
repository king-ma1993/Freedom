package com.myl.androidapkplugin.host.plugin

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.text.TextUtils
import androidx.collection.ArrayMap
import com.myl.androidapkplugin.factory.PluginApplicationFactory
import com.myl.androidapkplugin.HostPluginApi
import com.myl.plugin_core.exception.PluginException
import com.myl.plugin_core.exception.PluginException.Companion.PLUGIN_INFO_IS_EMPTY
import com.myl.plugin_core.proxy.DelegateProviderFactory
import com.myl.plugin_core.proxy.DelegateProviderFactory.DEFAULT_KEY
import com.myl.plugin_core.util.FileUtils
import com.myl.plugin_core.util.LogUtils
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.manager.client.PluginManagerBinderClient
import com.myl.plugin_core.manager.client.PluginManagerService
import com.myl.plugin_core.manager.server.PluginServerInfo
import com.myl.plugin_core.proxy.DelegateProvider
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

object HostPluginManager {


    private val PLUGINS = HashMap<String, PluginInfo>()

    private const val TAG = "HostPluginManager"
    private const val PLUGINCOMPONENTMANAGERCLASSNAME =
        "com.myl.androidapkplugin.pluginlib.proxy.ComponentDelegateProvider"

    private val plugins = HashMap<String, PluginInfo>()

    /**
     * 存放插件service的信息
     */
    private val mCacheServiceInfo: MutableMap<ComponentName, ServiceInfo> = ArrayMap()

    fun findPluginServiceInfo(componentName: ComponentName?): ServiceInfo? {
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


    /**
     * 防止绑定service重入
     */
    private val mServiceConnected = AtomicBoolean(false)
    private var iPluginManagerBinder: PluginManagerBinderClient? = null


    private fun getCachePluginInfo(packageName: String?): PluginInfo? {
        return plugins[packageName]
    }

    fun bindServerBinder() {
        if (mServiceConnected.get()) {
            LogUtils.d(TAG, "bindServerBinder service is connecting")
            return
        }
        mServiceConnected.set(true)
        val context = HostPluginApi?.getHostContext()
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


            }, BIND_AUTO_CREATE)
            LogUtils.d(TAG, "bindServerBinder isSuccess:$isBind")
        }

    }

    private fun getPluginManagerBinderClient(iPluginManagerBinder: IBinder): PluginManagerBinderClient {
        return PluginManagerBinderClient(iPluginManagerBinder)
    }

    private fun needToUpdatePlugin(packageInfo: PackageInfo?): Boolean {
        val cachePluginInfo = getCachePluginInfo(packageInfo?.packageName)
        cachePluginInfo ?: return true
        if (packageInfo?.versionCode!! >
            cachePluginInfo.pluginVersion!!
        ) {
            return true
        }
        return false
    }

    fun convertPluginIntent(intent: Intent): Intent? {
        return iPluginManagerBinder?.convertPluginIntent(intent)
    }

    /**
     * 加载插件
     */
    private fun loadPlugin(path: String?): PluginInfo? {
        val flags =
            (PackageManager.GET_META_DATA or PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
                    or PackageManager.GET_PROVIDERS or PackageManager.GET_RECEIVERS)

        val context = HostPluginApi?.getHostContext()!!
        val packageInfo = context.packageManager.getPackageArchiveInfo(path, flags)
        // 1. 读取APK内容
        if (!needToUpdatePlugin(packageInfo)) {
            return getCachePluginInfo(packageInfo.packageName)
        }
        val pluginServerInfo = iPluginManagerBinder?.loadPlugin(path) ?: return null

        val localPluginPath = pluginServerInfo?.localPluginPath
        val libSoPath = pluginServerInfo.libSoPath

        packageInfo?.applicationInfo?.apply {
            sourceDir = localPluginPath
            publicSourceDir = localPluginPath
            nativeLibraryDir = libSoPath
        }

        val pluginInfo = PluginInfo(packageInfo, pluginServerInfo, context)

        context?.apply {

            PluginApplicationFactory.createPluginApplication(
                pluginInfo,
                HostPluginApi.getHostContext() as Application
            )
            injectActivityDelegate(pluginInfo)
            plugins[packageName] = pluginInfo
            return pluginInfo
        }
        return null
    }

    private fun injectActivityDelegate(pluginInfo: PluginInfo) {
        val componentDelegateProviderClass =
            pluginInfo.mPluginClassLoader?.loadClass(PLUGINCOMPONENTMANAGERCLASSNAME)
        val constructor = componentDelegateProviderClass?.getConstructor(
            PluginInfo::class.java,
            Context::class.java
        )
        val componentDelegateProvider =
            constructor?.newInstance(pluginInfo, HostPluginApi.getHostContext())
        DelegateProviderFactory.setDelegateProvider(
            DEFAULT_KEY,
            componentDelegateProvider as DelegateProvider
        )
    }


//    private fun getCachePluginInfo(packageName: String?): PluginInfo? {
//        return PLUGINS[packageName]
//    }

//    private fun needToUpdatePlugin(packageInfo: PackageInfo?): Boolean {
//        val cachePluginInfo = getCachePluginInfo(packageInfo?.packageName)
//        cachePluginInfo ?: return true
//        if (packageInfo?.versionCode!! >
//            cachePluginInfo.pluginVersion!!
//        ) {
//            return true
//        }
//        return false
//    }

    /**
     * @param path
     * @return
     */
    fun pluginDownloaded(path: String?): PluginInfo? {

        val info = loadPlugin(path)
        if (info != null) {
            //todo 回调安装成功
        }

        return info
    }


    /**
     * 获取插件的缓存
     * @return
     */
    fun getPlugin(name: String?): PluginInfo? {
        synchronized(PLUGINS) {
            return PLUGINS[name]
        }
    }

    fun findPluginByComponentName(componentName: ComponentName?): PluginInfo? {
        return getPlugin(componentName?.packageName)
    }

}