package com.myl.androidapkplugin

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.myl.androidapkplugin.host.plugin.HostPluginManager
import com.myl.androidapkplugin.pluginlib.service.PluginServiceManager
import com.myl.androidapkplugin.host.plugin.PluginComponentManager
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.util.LogUtils
import java.io.File

object HostPluginApi {
    private const val TAG = "HostPluginApi"
    private var context: Context? = null

    fun init(context: Application) {
        HostPluginApi.context = context
        HostPluginManager.bindServerBinder()
    }

    fun getHostContext(): Context? {
        return context
    }

    /**
     * 安装或升级此插件
     *
     *
     * 注意：
     *
     *
     * 1、这里只将APK移动（或复制）到“插件路径”下，不释放优化后的Dex和Native库，不会加载插件
     *
     *
     *
     * 2、此方法是【同步】的，耗时较少
     *
     *
     * 3、不会触发插件“启动”逻辑，因此只要插件“当前没有被使用”，再次调用此方法则新插件立即生效
     *
     * @param path 插件安装的地址。必须是“绝对路径”。通常可以用context.getFilesDir()来做
     * @return 安装成功的插件信息，外界可直接读取
     */
    fun install(path: String): PluginInfo? {
        require(!TextUtils.isEmpty(path))

        // 判断文件合法性
        val file = File(path)
        if (!file.exists()) {
            LogUtils.e(TAG, "install: File not exists. path=$path")
        } else if (!file.isFile) {
            LogUtils.e(TAG, "install: Not a valid file. path=$path")
        }
        return HostPluginManager.pluginDownloaded(path)
    }


    /**
     * 创建一个用来定向到插件组件的Intent
     *
     *
     * @param pluginName 插件包名
     * @param cls        目标类全名
     * @return 可以被插件识别的Intent
     */
    fun createIntent(pluginName: String, cls: String): Intent {
        val intent = Intent()
        intent.component =
            createComponentName(pluginName, cls)
        return intent
    }

    /**
     * 创建一个用来定向到插件组件的ComponentName，其Key为插件名，Value为目标组件的类全名
     *
     * @param pluginName 插件名
     * @param cls        目标组件全名
     * @return 一个修改过的ComponentName对象
     * @since 1.0.0
     */
    private fun createComponentName(pluginName: String, cls: String): ComponentName {
        return ComponentName(pluginName, cls)
    }

    /**
     * 开启一个插件的Activity
     *
     *
     * 其中Intent的ComponentName的Key应为插件包名，可使用createIntent方法来创建Intent对象
     *
     * @param context Context对象
     * @param intent  要打开Activity的Intent，其中ComponentName的Key必须为插件名
     * @return 插件Activity是否被成功打开？
     * @see .createIntent
     * @since 1.0.0
     */
    fun startActivity(context: Context, intent: Intent): Boolean {
//        val cn = intent.component
//            ?:
//            return false
//        val plugin = cn.packageName
//        val cls = cn.className
        return PluginComponentManager.startPluginActivity(
            context,
            intent
        )
    }

//    fun startService(serviceIntent: Intent): ComponentName? {
//        return PluginServiceManager.startPluginService(serviceIntent)
//    }

}