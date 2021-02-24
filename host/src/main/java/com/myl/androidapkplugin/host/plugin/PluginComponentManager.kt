package com.myl.androidapkplugin.host.plugin

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.*
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.util.LogUtils
import com.myl.plugin_core.Constant

object PluginComponentManager {

    private const val TAG = "PluginComponentManager"


    /**
     * 内部接口
     *
     * @param context  应用上下文或者Activity上下文
     * @param intent   Intent对象
     * @param pluginPkgName   插件名
     * @param activity 待启动的activity类名
     */
    fun startPluginActivity(
        context: Context,
        intent: Intent
    ): Boolean {

        // WARNING：千万不要修改intent内容，尤其不要修改其ComponentName
        // 因为一旦分配坑位有误（或压根不是插件Activity），则外界还需要原封不动的startActivity到系统中
        // 可防止出现“本来要打开宿主，结果被改成插件”，进而无法打开宿主Activity的问题

        // 缓存打开前的Intent对象，里面将包括Action等内容
//        val from = Intent(intent)
        // 帮助填写打开前的Intent的ComponentName信息（如有。没有的情况如直接通过Action打开等）
//        if (!TextUtils.isEmpty(pluginPkgName) && !TextUtils.isEmpty(activity)) {
//            from.component = ComponentName(pluginPkgName, activity)
//        }
        val proxyIntent = HostPluginManager.convertPluginIntent(intent)
//            convertPluginActivityIntent(intent)
        proxyIntent?.apply {
            if (context !is Activity) {
                proxyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(proxyIntent)
            return true
        }
        //todo 是否需要回调启动activity成功
        return false
    }
}