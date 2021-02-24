package com.myl.androidapkplugin.pluginlib.fragment

import android.content.DialogInterface
import android.os.Bundle

open class PluginDialogFragment : PluginFragment() {


    private fun getContainerDialogFragment(): ContainerDialogFragment {
        return mContainerFragment as ContainerDialogFragment
    }

    open fun setStyle(style: Int, theme: Int) {
        if (mIsAppCreateFragment) {
            getContainerDialogFragment().setStyle(style, theme)
        }
    }

    open fun setCancelable(cancelable: Boolean) {
        if (mIsAppCreateFragment) {
            getContainerDialogFragment().setCancelable(cancelable)
        }
    }

    open fun getDialog(): PluginDialog {
        return getContainerDialogFragment().getDialog() as PluginDialog
    }

    open fun getTheme(): Int {
        return getContainerDialogFragment().getTheme()
    }

    open fun getShowsDialog(): Boolean {
        return getContainerDialogFragment().getShowsDialog()
    }

    open fun setShowsDialog(showsDialog: Boolean) {
        getContainerDialogFragment().setShowsDialog(showsDialog)
    }

    open fun dismiss() {
        getContainerDialogFragment().dismiss()
    }

    open fun dismissAllowingStateLoss() {
        getContainerDialogFragment().dismissAllowingStateLoss()
    }

    open fun show(manager: PluginFragmentManager, tag: String) {
        getContainerDialogFragment().show(manager.hostFragmentManager, tag)
    }

    open fun onCreateDialog(savedInstanceState: Bundle?): PluginDialog {
        return PluginDialog(getActivity()!!, getTheme())
    }

    open fun onDismiss(dialog: DialogInterface) {
        getContainerDialogFragment().superOnDismiss(dialog)
    }

    open fun onCancel(dialog: DialogInterface) {}
}