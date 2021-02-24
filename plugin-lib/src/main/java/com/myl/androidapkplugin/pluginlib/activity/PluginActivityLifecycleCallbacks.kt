package com.myl.androidapkplugin.pluginlib.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface PluginActivityLifecycleCallbacks {

    abstract fun onActivityCreated(activity: PluginActivity?, savedInstanceState: Bundle?)

    abstract fun onActivityStarted(activity: PluginActivity?)

    abstract fun onActivityResumed(activity: PluginActivity?)

    abstract fun onActivityPaused(activity: PluginActivity?)

    abstract fun onActivityStopped(activity: PluginActivity?)

    abstract fun onActivitySaveInstanceState(activity: PluginActivity?, outState: Bundle?)

    abstract fun onActivityDestroyed(activity: PluginActivity?)

    class Wrapper(
        internal val pluginActivityLifecycleCallbacks: PluginActivityLifecycleCallbacks,
        internal val runtimeObject: Any
    ) :
        Application.ActivityLifecycleCallbacks {

        private fun getPluginActivity(activity: Activity): PluginActivity? {
            return if (activity is com.myl.plugin_core.activity.PluginContainerActivity) {
                activity.getPluginActivity()!! as PluginActivity
            } else {
                null
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityCreated(
                    pluginActivity,
                    savedInstanceState
                )
            }
        }

        override fun onActivityStarted(activity: Activity) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityStarted(pluginActivity)
            }
        }

        override fun onActivityResumed(activity: Activity) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityResumed(pluginActivity)
            }
        }

        override fun onActivityPaused(activity: Activity) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityPaused(pluginActivity)
            }
        }

        override fun onActivityStopped(activity: Activity) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityStopped(pluginActivity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivitySaveInstanceState(
                    pluginActivity,
                    outState
                )
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            val pluginActivity = getPluginActivity(activity)
            if (checkOwnerActivity(pluginActivity)) {
                pluginActivityLifecycleCallbacks.onActivityDestroyed(pluginActivity)
            }
        }

        /**
         * 检测Activity是否属于当前Application所在的插件
         *
         * @param activity 插件Activity
         * @return 是否属于当前Application所在的插件 true属于
         */
        private fun checkOwnerActivity(activity: PluginActivity?): Boolean {
            return activity != null && activity.getApplication() === runtimeObject
        }
    }
}