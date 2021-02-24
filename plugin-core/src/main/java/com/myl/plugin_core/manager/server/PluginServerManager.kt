package com.myl.plugin_core.manager.server

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import com.myl.plugin_core.Constant
import com.myl.plugin_core.Constant.ORIGIN_SERVICE_INFO
import com.myl.plugin_core.Constant.ORIGIN_SERVICE_INTENT
import com.myl.plugin_core.manager.PluginManager
import com.myl.plugin_core.util.LogUtils

class PluginServerManager(val context: Context) {

    companion object {
        const val TAG = "PluginServerManager"
        private const val PACKAGE_NAME = "com.myl.plugin_core.activity"
        private const val MODE_STANDARD_ACTIVITY = "$PACKAGE_NAME.StandardContainerActivity"
        private const val MODE_SINGLETOP_ACTIVITY = "$PACKAGE_NAME.SingleTopContainerActivity"
        private const val MODE_SINGLETASK_ACTIVITY = "$PACKAGE_NAME.SingleTaskContainerActivity"
        private const val MODE_SINGLE_INSTANCE_ACTIVITY =
            "$PACKAGE_NAME.SingleInstanceContainerActivity"
        private const val CONTAINER_SERVICE = "com.myl.plugin_core.service.ContainerService"
    }

    /**
     *  通过插件路径获取对应的PluginManager
     */
    private val mPluginManagerMap: HashMap<String, PluginManager> = hashMapOf()

    private val mPluginManagerMap2: HashMap<String, PluginManager> = hashMapOf()

    fun loadPlugin(path: String?): PluginServerInfo? {
        path?.apply {
            val pluginManager = getPluginManager(path)
            return pluginManager.parsePlugin()
        }
        return null
    }

    private fun getPluginManager(path: String): PluginManager {
        val pluginManager = if (mPluginManagerMap[path] != null) {
            mPluginManagerMap[path]
        } else {
            val flags =
                (PackageManager.GET_META_DATA or PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES
                        or PackageManager.GET_PROVIDERS or PackageManager.GET_RECEIVERS)
            val packageInfo = context.packageManager.getPackageArchiveInfo(path, flags)
            val manager = PluginManager(packageInfo, path, context)
            mPluginManagerMap2[packageInfo.packageName] = manager
            manager
        }
        return pluginManager!!
    }

    fun findPluginReceiverInfo(
        receiverClassName: String,
        pluginName: String
    ): PluginReceiverInfo {
        val pluginManager = getPluginManagerByPakageName(pluginName)
        val intentFilters = pluginManager?.findReceiverIntentFilter(receiverClassName)
        return PluginReceiverInfo(receiverClassName, pluginName, intentFilters)
    }

    fun findPluginActivityInfo(componentName: ComponentName?): ActivityInfo? {
        componentName?.apply {
            val pluginManager = getPluginManagerByPakageName(packageName)
            return pluginManager?.findPluginActivityInfo(componentName)
        }
        return null
    }

    fun findPluginServiceInfo(componentName: ComponentName?): ServiceInfo? {
        componentName?.apply {
            val pluginManager = getPluginManagerByPakageName(packageName)
            return pluginManager?.findPluginServiceInfo(componentName)
        }
        return null
    }

    fun convertServiceIntent(service: Intent?): Intent? {
        val component = service?.component
        LogUtils.d(
            TAG,
            "start pluginPkgName service: intent=$service pluginPkgName=${component?.packageName} service=${component?.className} "
        )
        if (component == null) {
            return null
        }
        val pluginManager = getPluginManagerByPakageName(component?.packageName)
        pluginManager?.apply {
            val pluginServiceInfo = pluginManager.findPluginServiceInfo(component)
            val containerServiceName = CONTAINER_SERVICE
            val newComponent = ComponentName(context, containerServiceName)
            val proxyServiceIntent = Intent(service)
            proxyServiceIntent.component = newComponent
            service.extras?.apply {
                proxyServiceIntent.putExtras(this)
            }
            proxyServiceIntent.putExtra(ORIGIN_SERVICE_INTENT, service)
            proxyServiceIntent.putExtra(ORIGIN_SERVICE_INFO, pluginServiceInfo)
            return proxyServiceIntent
        }
        return null
    }


    fun convertPluginIntent(intent: Intent?): Intent {
        val component = intent?.component
        LogUtils.d(
            TAG,
            "start pluginPkgName activity: intent=$intent pluginPkgName=${component?.packageName} activity=${component?.className} "
        )
        //加载解析出来的这个插件的activity信息
        val pluginCn =
            loadContainerActivity(
                component
            )
        //启动代理activity的intent
        val proxyIntent = Intent(intent)
        proxyIntent.component = pluginCn
        proxyIntent.replaceExtras(intent!!)
        proxyIntent.putExtra(Constant.ORIGIN_ACTIVITY_INTENT, intent)
        return proxyIntent
    }

    private fun getPluginManagerByPakageName(packageName: String): PluginManager? {
        return mPluginManagerMap2[packageName]
    }


    private fun loadContainerActivity(
        component: ComponentName?
    ): ComponentName? {
        if (component == null) {
            return null
        }
        val pluginManager = getPluginManagerByPakageName(component?.packageName)
        pluginManager?.apply {
            //根据ComponentName查询插件ActivityInfo
            val pluginActivityInfo = pluginManager.findPluginActivityInfo(component)
            val pluginActivityName = component?.className
            val launchMode = pluginActivityInfo?.launchMode

            val proxyActivityName =
                when (launchMode) {
                    ActivityInfo.LAUNCH_SINGLE_TOP -> MODE_SINGLETOP_ACTIVITY
                    ActivityInfo.LAUNCH_SINGLE_INSTANCE -> MODE_SINGLE_INSTANCE_ACTIVITY
                    ActivityInfo.LAUNCH_SINGLE_TASK -> MODE_SINGLETASK_ACTIVITY
                    else -> MODE_STANDARD_ACTIVITY
                }

            val msg = String.format(
                "resolve %s Activity for %s proxy is %s",
                launchModeToString(
                    launchMode
                ), pluginActivityName, proxyActivityName
            )
            LogUtils.d(TAG, msg)
            //返回代理ComponentName
            return ComponentName(context, proxyActivityName)
        }
        return component
    }


    private fun launchModeToString(launchMode: Int?): String {
        var mode = "standard"
        when (launchMode) {
            ActivityInfo.LAUNCH_SINGLE_TOP -> mode = "singleTop"
            ActivityInfo.LAUNCH_SINGLE_INSTANCE -> mode = "singleInstance"
            ActivityInfo.LAUNCH_SINGLE_TASK -> mode = "singleTask"
        }

        return mode
    }

}