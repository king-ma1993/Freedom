package com.myl.androidapkplugin.pluginlib.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.myl.androidapkplugin.pluginlib.activity.PluginActivity
import com.myl.plugin_core.activity.PluginContainerActivity

class PluginDialog : Dialog {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener) {
    }

    fun setOwnerPluginActivity(activity: PluginActivity) {
        val hostActivity = activity.hostActivityDelegator?.getHostActivity() as Activity
        setOwnerActivity(hostActivity)
    }

    fun getOwnerPluginActivity(): PluginActivity? {
        val ownerActivity = ownerActivity as PluginContainerActivity?
        return ownerActivity?.getPluginActivity() as PluginActivity
    }
}