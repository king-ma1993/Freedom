package com.myl.plugin_core.activity

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.myl.plugin_core.proxy.DelegateProviderFactory
import com.myl.plugin_core.proxy.DelegateProviderFactory.getDelegateProviderKey
import com.myl.plugin_core.proxy.HostActivityInterface
import com.myl.plugin_core.proxy.IHostActivity
import com.myl.plugin_core.proxy.method.PluginActivityMethodProxy
import com.myl.plugin_core.util.LogUtils
import kotlin.system.exitProcess

open class PluginContainerActivity : BasePluginContainerActivity(), HostActivityInterface,
    IHostActivity {
    companion object {
        const val TAG = "PluginContainerActivity"
    }

    private var isBeforeOnCreate = true

//    private fun initDelegate() {
//
//    }

    init {
//        LogUtils.d(TAG, "PluginContainerActivity init:${android.os.Process.myPid()}")
        val delegateProvider = DelegateProviderFactory.getDelegateProvider(getDelegateProviderKey())
        LogUtils.d(TAG, "delegateProvider:$delegateProvider")
        delegateProvider?.apply {
            val delegate =
                delegateProvider.getHostActivityDelegate(this@PluginContainerActivity.javaClass)
            delegate.setHostActivityInterface(this@PluginContainerActivity)
            //PluginActivityDelegateProxy
            pluginActivityMethodInterface = delegate
        }
    }


    fun getPluginActivity(): Any? {
        return getPluginActivityMethodProxy().getPluginActivity()
    }


    private fun getPluginActivityMethodProxy(): PluginActivityMethodProxy {
        return pluginActivityMethodInterface as PluginActivityMethodProxy
    }

    override fun getImplementActivity(): Activity {
        return this
    }

    override fun getImplementWindow(): Window {
        return window
    }

    override fun getHostActivity(): IHostActivity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        isBeforeOnCreate = false
//        initDelegate()
        if (getPluginActivityMethodProxy() != null) {
            getPluginActivityMethodProxy().onCreate(savedInstanceState)
        } else {
            //这里是进程被杀后重启后走到，当需要恢复fragment状态的时候，由于系统保留了TAG，会因为找不到fragment引起crash
            super.onCreate(null)
            finish()
            exitProcess(0)
        }
//        applyPluginTheme()
//        isOnCreate = true
    }

    /**
     * Theme一旦设置了就不能更换Theme所在的Resouces了，见[Resources.Theme.setTo]
     * 而Activity在OnCreate之前需要设置Theme和使用Theme。我们需要在Activity OnCreate之后才能注入插件资源。
     * 这就需要在Activity OnCreate之前不要调用Activity的setTheme方法，同时在getTheme时返回宿主的Theme资源。
     * 注：[Activity.setTheme]会触发初始化Theme，因此不能调用。
     */
    private var mHostTheme: Resources.Theme? = null

    override fun getTheme(): Resources.Theme? {
        LogUtils.d(TAG, "getTheme: isBeforeOnCreate:$isBeforeOnCreate " + Log.getStackTraceString(Throwable()))
        if (isBeforeOnCreate) {
            if (mHostTheme == null) {
                mHostTheme = super.getResources().newTheme()
            }
            return mHostTheme
        } else {
            return super.getTheme()
        }
    }

    override fun setTheme(resid: Int) {
        if (!isBeforeOnCreate) {
            super.setTheme(resid)
        }
    }

}