package com.myl.plugin_core.info

import android.content.ComponentName
import android.content.Context
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import com.myl.plugin_core.loader.PluginClassLoader
import com.myl.plugin_core.manager.PluginManager
import com.myl.plugin_core.manager.server.PluginServerInfo
import com.myl.plugin_core.res.PluginResources
import com.myl.plugin_core.util.FileUtils
import com.myl.plugin_core.util.LogUtils
import java.io.File

class PluginInfo(
    var packageInfo: PackageInfo,
    var pluginServerInfo: PluginServerInfo,
    var hostContext: Context
) {
    var pluginVersion: Int = 0
    var packageName: String = ""
    var mPluginClassLoader: PluginClassLoader? = null
    var mPluginAssetManager: AssetManager? = null
    var mPluginResources: Resources? = null
    var mPluginApplicationInfo: ApplicationInfo? = null
    var mApplication: Context? = null
    var localPluginPath = pluginServerInfo.localPluginPath


    /**
     *  存放插件receivers的name信息
     */
    val mReceiverInfo: HashMap<ComponentName, ActivityInfo> = hashMapOf()

    /**
     * 通过插件APK的MetaData来初始化PluginInfo
     *
     *
     * 注意：框架内部接口，外界请不要直接使用
     */

    companion object {

        const val TAG = "PluginInfo"

    }


    init {
        pluginVersion = packageInfo.versionCode
        packageName = packageInfo.packageName
        mPluginApplicationInfo = packageInfo.applicationInfo
        initPlugin()
        initReceiver()
    }

    private fun initReceiver() {
        LogUtils.d(PluginManager.TAG, "packageInfo.receiver = :${packageInfo?.receivers}")
        val receivers = packageInfo?.receivers ?: return

        for (activityInfo in receivers) {
            mReceiverInfo[ComponentName(activityInfo.packageName, activityInfo.name)] =
                activityInfo
        }
    }


    private fun initPlugin() {
        initPluginAssetManager()
        initPluginResource()
        mPluginClassLoader = PluginClassLoader(
            this,
            hostContext?.classLoader!!
        )
    }

    private fun initPluginAssetManager() {
        mPluginAssetManager =
            createAssetManager(hostContext)
    }

    @Throws(Throwable::class)
    private fun createAssetManager(ctx: Context?): AssetManager? {
        ctx?.apply {
            val pm = ctx.packageManager
            val res = pm.getResourcesForApplication(packageInfo.applicationInfo)
            return res.assets
        }
        return null
    }


    private fun initPluginResource() {
        hostContext?.apply {
            mPluginResources = PluginResources(
                mPluginAssetManager!!,
                resources
            )
        }
    }


    /**
     * 获取优化后dex存放目录
     */
    fun getOdexDirPath(): String? {
        return pluginServerInfo.odexDirPath
    }

    fun getLibSoPath(): String? {
        return pluginServerInfo.libSoPath
    }


}