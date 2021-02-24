package com.myl.androidplugin

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.os.Bundle
import com.myl.androidapkplugin.HostPluginApi
import com.myl.plugin_core.util.LogUtils

class DemoApp : Application() {

    companion object {
        const val TAG = "DemoApp"
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                LogUtils.i(TAG, " onActivityPaused -->${activity}")
            }

            override fun onActivityDestroyed(activity: Activity) {
                LogUtils.i(TAG, " onActivityDestroyed -->${activity}")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                LogUtils.i(TAG, " onActivitySaveInstanceState -->${activity}")
            }

            override fun onActivityStopped(activity: Activity) {
                LogUtils.i(TAG, " onActivityStopped -->${activity}")
            }

            override fun onActivityStarted(activity: Activity) {
                LogUtils.i(TAG, " onActivityStarted -->${activity}")

            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                LogUtils.i(TAG, " onActivityCreated -->${activity}")
            }

            override fun onActivityResumed(activity: Activity) {
                LogUtils.i(TAG, " onActivityResumed -->${activity}")
            }

        })
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HostPluginApi.init(this)
    }

    override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        super.registerActivityLifecycleCallbacks(callback)
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {
        super.registerComponentCallbacks(callback)
    }
}