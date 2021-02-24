package com.myl.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.myl.demo.util.LogUtils

class PluginDemoApp : Application() {
    companion object {
        const val TAG = "PluginDemoApp"
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.d(TAG, "PluginDemoApp onCreate")
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
}