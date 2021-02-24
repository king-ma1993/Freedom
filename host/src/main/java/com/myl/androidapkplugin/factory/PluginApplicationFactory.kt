package com.myl.androidapkplugin.factory

import android.app.Application
import android.content.Context
import com.myl.plugin_core.info.PluginInfo
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.plugin_core.util.ReflectUtils

object PluginApplicationFactory {

    fun createPluginApplication(
        pluginInfo: PluginInfo?,
        hostApplication: Application?
    ): PluginInfo? {
        pluginInfo?.apply {
            val applicationInfo = pluginInfo?.mPluginApplicationInfo
            val appClassName = applicationInfo?.className
                ?: PluginApplication::class.java.name
//            val pluginApplication =
//                pluginInfo?.mPluginClassLoader?.loadClass(appClassName)?.newInstance() as PluginApplication
            val pluginApplicationClass = pluginInfo?.mPluginClassLoader?.loadClass(appClassName)

//            val buildPluginApplcaitonMethod = pluginApplicationClass?.getMethod("buildPluginApplcation",)
            val pluginApplication = pluginApplicationClass?.newInstance()
            val methodParamTypes = arrayOf(PluginInfo::class.java, Application::class.java)
            ReflectUtils.invokeMethod(
                pluginApplication,
                "buildPluginApplcation",
                methodParamTypes,
                pluginApplication,
                pluginInfo,
                hostApplication
            )

            ReflectUtils.invokeMethod(
                pluginApplication,
                "onCreate", null, pluginApplication
            )

            pluginInfo.mApplication = pluginApplication as Context

//            pluginApplication.buildPluginApplcation(pluginInfo, hostApplication)
            return pluginInfo
        }
        return null
    }
}