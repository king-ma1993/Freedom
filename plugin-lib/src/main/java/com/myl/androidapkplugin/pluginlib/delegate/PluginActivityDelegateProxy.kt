package com.myl.androidapkplugin.pluginlib.delegate

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import com.myl.plugin_core.Constant
import com.myl.plugin_core.info.PluginInfo
import com.myl.plugin_core.util.LogUtils
import com.myl.androidapkplugin.pluginlib.activity.PluginActivity
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.plugin_core.proxy.HostActivityInterface
import com.myl.plugin_core.proxy.method.PluginActivityMethodProxy

/**
 *PluginActivityDelegateProxy ShadowActivityDelegate
 */
class PluginActivityDelegateProxy(private var pluginInfo: PluginInfo) : BaseActivityDelegate(),
    PluginActivityMethodProxy {

    companion object {
        const val TAG = "PluginActivityDelegateProxy"
    }

    private lateinit var mHostActivityDelegator: HostActivityInterface
//    private
    private var mThemeId: Int = 0
    private var mTheme: Resources.Theme? = null


    override fun setHostActivityInterface(delegator: HostActivityInterface) {
        //设置为了PluginContainerActivity
        mHostActivityDelegator = delegator
    }

    override fun getPluginActivity(): Any {
        return pluginActivity!!
    }

    override fun onNavigateUpFromChild(arg0: Activity): Boolean {
        return false
    }

    override fun onChildTitleChanged(arg0: Activity, arg1: CharSequence) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.d(TAG, "PluginActivityDelegateProxy onCreate")
        val pluginIntent = mHostActivityDelegator.getIntent()
        var pluginActivityInfo: ActivityInfo? = null
        pluginIntent?.apply {
            val originIntent: Intent = getParcelableExtra(Constant.ORIGIN_ACTIVITY_INTENT)
            if (originIntent == null) {
                finish()
                return
            }
            var mTargetActivity: PluginActivity? = null
            val mPluginActivityComponentName = originIntent.component
            mPluginActivityComponentName?.apply {
                val mTargetActivityName = className
                val targetActivityCls =
                    loadTargetActivity(mTargetActivityName)
                mTargetActivity = targetActivityCls?.newInstance() as PluginActivity?
                fillPluginActivity(mTargetActivity)
            }

            pluginActivity = mTargetActivity

            pluginActivityInfo =
                PluginLibManager?.findPluginActivityInfo(mPluginActivityComponentName)
            //处理插件AndroidManifest.xml 中注册的WindowSoftInputMode
            pluginActivityInfo?.apply {
                mHostActivityDelegator?.getWindow().setSoftInputMode(softInputMode)
//                applyPluginTheme(theme)

            }
            //使PluginActivity替代ContainerActivity接收Window的Callback
            mHostActivityDelegator?.getWindow().callback = pluginActivity
            mPluginActivityInit = true
        }
        applyPluginTheme(pluginActivityInfo?.theme!!)
        super.onCreate(savedInstanceState)

    }

    private fun fillPluginActivity(pluginActivity: PluginActivity?) {
        pluginActivity?.apply {
            hostActivityDelegator = mHostActivityDelegator
            mPluginResources = pluginInfo?.mPluginResources
            mPluginClassLoader = pluginInfo?.mPluginClassLoader
            mPluginApplication = pluginInfo?.mApplication as PluginApplication
            setPluginApplicationInfo(pluginInfo?.mPluginApplicationInfo)
            //setHostContextAsBase会调用插件Activity的attachBaseContext方法，
            //有可能会执行业务Activity覆盖的逻辑。
            //所以，这个调用要放在最后。
            //这里负值是为了插件activity的mbase不为null,因为很多逻辑例如startActivity是
            // 通过插件的mBase.startActivity(intent, options)达成
            setHostContextAsBase(mHostActivityDelegator.getHostActivity() as Context)
        }
    }

    private fun loadTargetActivity(className: String?): Class<*>? {
        return pluginInfo?.mPluginClassLoader?.loadClass(className)
    }

    private fun applyPluginTheme(themeId: Int) {
        setPluginTheme(getPluginThemeId(themeId))
    }

    private fun setPluginTheme(themeId: Int) {
        if (mThemeId == themeId || themeId == 0) {
            return
        }

        mThemeId = themeId
        val first = mTheme == null
        LogUtils.d(TAG, "setPluginTheme: themeId:$mThemeId")
        mTheme = pluginInfo.mPluginResources?.newTheme()
        val theme = mHostActivityDelegator.getHostActivity().getImplementActivity().theme
        if (theme != null) {
            mTheme?.setTo(theme)
        }
        onApplyThemeResource(mTheme!!, mThemeId, first)
        pluginActivity?.theme = mTheme
//        val theme2 = mHostActivityDelegator.getHostActivity().getImplementActivity().theme
//        LogUtils.d(TAG, "setPluginTheme after: themeId:$theme2")
    }

    private fun getPluginThemeId(activityThemeid: Int): Int {
        var theme = activityThemeid
        val activityInfo = PluginLibManager?.findPluginActivityInfo(
            ComponentName(pluginInfo!!.packageName, pluginActivity!!.javaClass?.name)
        )
        if (null != activityInfo) {
            theme = activityInfo!!.theme
        }
        if (theme != 0) {
            return theme
        }

        val applicationInfo = pluginInfo?.mPluginApplicationInfo
        applicationInfo?.apply {
            theme = applicationInfo.theme
        }

        if (0 == theme) {
            theme = android.R.style.Theme_Holo_Light_NoActionBar
        }
        return theme
    }

}