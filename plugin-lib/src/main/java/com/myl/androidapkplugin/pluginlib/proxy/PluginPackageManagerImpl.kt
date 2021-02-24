package com.myl.androidapkplugin.pluginlib.proxy

import android.content.ComponentName
import android.content.pm.*
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.plugin_core.info.PluginInfo
import java.util.HashMap

class PluginPackageManagerImpl(
    private val plugin :PluginInfo,
    private val hostPackageManager: PackageManager
) : PluginPackageManager {

    override fun getApplicationInfo(packageName: String?, flags: Int): ApplicationInfo {
        getPluginInfo(packageName)?.apply {
            return packageInfo.applicationInfo
        }
        return hostPackageManager.getApplicationInfo(packageName, flags)
    }

    override fun getActivityInfo(component: ComponentName?, flags: Int): ActivityInfo {
        val activityInfo = PluginLibManager.findPluginActivityInfo(component)
        activityInfo?.apply {
            return this
        }
        return hostPackageManager.getActivityInfo(component, flags)
    }

    override fun getPackageInfo(packageName: String?, flags: Int): PackageInfo {
        getPluginInfo(packageName)?.apply {
            return packageInfo
        }
        return hostPackageManager.getPackageInfo(packageName, flags)
    }

    override fun resolveContentProvider(name: String?, flags: Int): ProviderInfo? {
        return null
    }


    private fun getPluginInfo(packageName: String?): PluginInfo? {
        return plugin
    }

}