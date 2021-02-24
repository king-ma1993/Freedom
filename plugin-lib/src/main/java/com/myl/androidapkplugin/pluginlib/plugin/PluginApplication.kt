package com.myl.androidapkplugin.pluginlib.plugin

import android.app.Application
import android.content.BroadcastReceiver
import android.content.ComponentCallbacks
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import com.myl.androidapkplugin.pluginlib.activity.PluginActivityLifecycleCallbacks
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.plugin_core.info.PluginInfo
import java.util.HashMap

/**
 * 插件的application,后面通过transform进行替换
 */
open class PluginApplication : PluginContext() {

    private var isCallOnCreate: Boolean = false
    private var mHostApplication: Application? = null
    private val mActivityLifecycleCallbacksMap =
        HashMap<PluginActivityLifecycleCallbacks, Application.ActivityLifecycleCallbacks>()
    private var mPluginInfo: PluginInfo? = null

    open fun buildPluginApplcation(pluginInfo: PluginInfo, hostApplication: Application?) {
        mPluginInfo = pluginInfo
        mPluginResources = pluginInfo.mPluginResources
        mPluginClassLoader = pluginInfo.mPluginClassLoader
        setPluginApplicationInfo(pluginInfo.mPluginApplicationInfo)
        mHostApplication = hostApplication
    }


    open fun onCreate() {
        //onCreate只调用一次
        if (isCallOnCreate) {
            return
        }
        PluginLibManager.bindServerBinder(mHostApplication!!)
        PluginLibManager.bindPluginInfo(mPluginInfo!!)
        mHostApplication?.registerComponentCallbacks(object : ComponentCallbacks2 {
            override fun onTrimMemory(level: Int) {
                this@PluginApplication.onTrimMemory(level)
            }

            override fun onConfigurationChanged(newConfig: Configuration) {
                this@PluginApplication.onConfigurationChanged(newConfig)
            }

            override fun onLowMemory() {
                this@PluginApplication.onLowMemory()
            }
        })
        registerStaticBroadcastReceiver(
            this,
            mPluginInfo!!
        )
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks) {
        mHostApplication?.registerComponentCallbacks(callback)
    }


    override fun unregisterComponentCallbacks(callback: ComponentCallbacks) {
        mHostApplication?.unregisterComponentCallbacks(callback)
    }


    open fun onConfigurationChanged(newConfig: Configuration) {
        //do nothing.
    }


    open fun onLowMemory() {
        //do nothing.
    }


    open fun onTrimMemory(level: Int) {
        //do nothing.
    }

    open fun registerActivityLifecycleCallbacks(callback: PluginActivityLifecycleCallbacks) {
        synchronized(mActivityLifecycleCallbacksMap) {
            val wrapper = PluginActivityLifecycleCallbacks.Wrapper(callback, this)
            mActivityLifecycleCallbacksMap[callback] = wrapper
            mHostApplication?.registerActivityLifecycleCallbacks(wrapper)
        }
    }


    open fun unregisterActivityLifecycleCallbacks(callback: PluginActivityLifecycleCallbacks) {
        synchronized(mActivityLifecycleCallbacksMap) {
            val activityLifecycleCallbacks = mActivityLifecycleCallbacksMap[callback]
            if (activityLifecycleCallbacks != null) {
                mHostApplication?.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
                mActivityLifecycleCallbacksMap.remove(callback)
            }
        }
    }

    override fun attachBaseContext(base: Context) {
        //do nothing.
    }


    private fun registerStaticBroadcastReceiver(context: Context, pluginInfo: PluginInfo) {
        val receivers = pluginInfo.mReceiverInfo

        for (receiver in receivers) {
            val receiverClassName = receiver.key.className
            val intentFilters =
                PluginLibManager.findPluginReceiverInfo(receiverClassName, pluginInfo.packageName)
                    ?.intentFilters

            if (intentFilters == null || intentFilters!!.isEmpty()) {
                continue
            }

            val pluginClassLoader = pluginInfo?.mPluginClassLoader
            val instance = pluginClassLoader?.loadClass(
                receiverClassName
            )?.newInstance() as BroadcastReceiver
            for (filter in intentFilters!!) {
                context.registerReceiver(instance, filter)
            }
        }
    }
}