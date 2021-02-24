package com.myl.androidapkplugin.pluginlib.proxy

import android.content.ComponentName
import android.content.Context
import com.myl.androidapkplugin.pluginlib.delegate.PluginActivityDelegateProxy
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.plugin_core.info.PluginInfo
import com.myl.androidapkplugin.pluginlib.service.PluginService
import com.myl.plugin_core.proxy.DelegateProvider
import com.myl.plugin_core.proxy.HostActivityInterface
import com.myl.plugin_core.service.PluginServiceMethodInterface

class ComponentDelegateProvider(var pluginInfo: PluginInfo, var hostContext: Context?) :
    DelegateProvider {

//    override fun getPluginServiceMethodInterface(
//        serviceComponent: ComponentName?
//    ): PluginServiceMethodInterface? {
//        serviceComponent ?: return null
//        val pluginServiceMethodInterface = findAlivePluginService(serviceComponent)
//        if (pluginServiceMethodInterface != null) {
//            return pluginServiceMethodInterface
//        }
//        val pluginService = newPluginInstance(serviceComponent)
//        fillPluginService(pluginService)
//        pluginService.onCreate()
//        mAliveServicesMap[serviceComponent] = pluginService
//        return pluginService
//    }
//
//    override fun destroyPluginServiceMethodInterface(serviceComponent: ComponentName?) {
//        serviceComponent?.apply {
//            val pluginServiceMethodInterface = findAlivePluginService(this)
//            mAliveServicesMap.remove(this)
//            pluginServiceMethodInterface?.onDestroy()
//        }
//    }

    // 所有已启动的service集合
    private val mAliveServicesMap = HashMap<ComponentName, PluginServiceMethodInterface>()

    override fun getHostActivityDelegate(delegator: Class<out HostActivityInterface>): com.myl.plugin_core.proxy.method.PluginActivityMethodProxy {
        return PluginActivityDelegateProxy(pluginInfo)
    }


//    private fun findAlivePluginService(componentName: ComponentName?): PluginServiceMethodInterface? {
//        return mAliveServicesMap[componentName]
//    }
//
//    private fun newPluginInstance(serviceComponentName: ComponentName): PluginService {
//        val pluginServiceName = serviceComponentName.className
//        return pluginInfo?.mPluginClassLoader?.loadClass(pluginServiceName)?.newInstance() as PluginService
//    }
}