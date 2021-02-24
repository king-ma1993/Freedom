package com.myl.plugin_core

import androidx.annotation.IntDef

object Constant {
    /**
     * "纯APK"插件存放目录
     */
    const val LOCAL_PLUGIN_APK_DIR = "plugin"
    /**
     * 存放原来的intent
     */
    const val ORIGIN_ACTIVITY_INTENT = "origin_intent"
    const val ORIGIN_SERVICE_INTENT = "origin_service_intent"
    const val ORIGIN_SERVICE_INFO = "origin_service_info"
    //    const val ORIGIN_ACTIVITY_INTENT = "origin_intent"
    // 插件优化后的 dex 目录（Android 5.0 及以上设备）
    const val OAT_DIR = "oat"

    // 插件优化后的 dex 目录（Android 5.0 以下设备）
    const val ODEX_DIR = "odex"

    // 插件 so 库目录
    const val LIB_DIR = "lib"

    const val ORIGIN_SERVICE_ACTION = "origin_service_action"
    const val SERVICE_BINDER_KEY = "service_binder_key"

    @Target(AnnotationTarget.TYPE)
    @IntDef(
        value = [INVALID_SERVICE_ACTION, START_SERVICE, STOP_SERVICE, BIND_SERVICE, UNBIND_SERVICE]
    )
    @kotlin.annotation.Retention
    annotation class PluginServiceAction

    const val INVALID_SERVICE_ACTION = -1
    const val START_SERVICE = 0
    const val STOP_SERVICE = 1
    const val BIND_SERVICE = 2
    const val UNBIND_SERVICE = 3
}