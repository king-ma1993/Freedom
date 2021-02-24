package com.myl.androidapkplugin.pluginlib.activity

import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import com.myl.plugin_core.exception.PluginException
import com.myl.plugin_core.exception.PluginException.Companion.UNSUPPORT_METHOD
import com.myl.androidapkplugin.pluginlib.fragment.PluginFragmentManager
import com.myl.androidapkplugin.pluginlib.plugin.PluginApplication
import com.myl.plugin_core.util.LogUtils
import android.view.LayoutInflater
import com.myl.androidapkplugin.pluginlib.plugin.PluginLayoutInflater


/**
 * PluginActivity ShadowActivity
 */
open class PluginActivity : BasePluginActivity() {

    private var mPluginFragmentManager: PluginFragmentManager? = null
    private var mFragmentManagerHash: Int = 0

    override fun getFragmentManager(): PluginFragmentManager {
        //获取host的fragmentManager
        val fragmentManager: FragmentManager? = hostActivityDelegator?.getFragmentManager()
//        LogUtils.d(TAG, "hhhhh")
        val hash = System.identityHashCode(fragmentManager)
        if (hash != mFragmentManagerHash) {
            mFragmentManagerHash = hash
            mPluginFragmentManager = PluginFragmentManager(fragmentManager)
        }
        return mPluginFragmentManager!!
    }

    override fun onNavigateUpFromChild(arg0: PluginActivity): Boolean {
        return false
    }

    override fun onChildTitleChanged(arg0: PluginActivity, arg1: CharSequence) {
    }

    override fun getParent(): PluginActivity? {
        return null
    }

    override fun navigateUpToFromChild(arg0: PluginActivity, arg1: Intent): Boolean {
        throw PluginException(UNSUPPORT_METHOD)
    }

    override fun startIntentSenderFromChild(
        arg0: PluginActivity,
        arg1: IntentSender,
        arg2: Int,
        arg3: Intent,
        arg4: Int,
        arg5: Int,
        arg6: Int,
        arg7: Bundle
    ) {
        throw PluginException(UNSUPPORT_METHOD)
    }

    override fun startIntentSenderFromChild(
        arg0: PluginActivity,
        arg1: IntentSender,
        arg2: Int,
        arg3: Intent,
        arg4: Int,
        arg5: Int,
        arg6: Int
    ) {
        throw PluginException(UNSUPPORT_METHOD)
    }

    override fun finishFromChild(arg0: PluginActivity) {
        throw PluginException(UNSUPPORT_METHOD)
    }

    override fun finishActivityFromChild(arg0: PluginActivity, arg1: Int) {
        throw PluginException(UNSUPPORT_METHOD)
    }

    var mPluginApplication: PluginApplication? = null

    override fun getApplication(): PluginApplication {
        return mPluginApplication!!
    }

    fun setHostContextAsBase(context: Context) {
        attachBaseContext(context)
    }

    override fun setRequestedOrientation(arg0: Int) {
        LogUtils.d(
            TAG, "setRequestedOrientation :" + Log.d(
                TAG,
                Log.getStackTraceString(Throwable())
            )
        )
        super.setRequestedOrientation(arg0)
    }

    //为了解决在清单文件里声明android:Onclick反射找不到方法的问题，因为渲染view的context是从LayoutInflater中获取的
    override fun setContentView(layoutId: Int) {
        val inflate = LayoutInflater.from(this).inflate(layoutId, null)
        hostActivityDelegator?.setContentView(inflate)
    }

    override fun getLayoutInflater(): LayoutInflater {
        val inflater = hostActivityDelegator?.getWindow()?.layoutInflater
        return PluginLayoutInflater.build(inflater!!, this)
    }
}