package com.myl.androidapkplugin.pluginlib.fragment

import android.annotation.TargetApi
import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Build
import android.view.View

class PluginFragmentTransaction(
    /*var pluginFragmentManager: PluginFragmentManager,*/
    var hostFragmentTransaction: FragmentTransaction?
) {


    fun add(fragment: PluginFragment, tag: String): PluginFragmentTransaction {
        hostFragmentTransaction?.add(getContainerFragment(fragment), tag)
        return this
    }

    fun add(containerViewId: Int, fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.add(containerViewId, getContainerFragment(fragment))
        return this
    }

    fun add(
        containerViewId: Int,
        fragment: PluginFragment,
        tag: String
    ): PluginFragmentTransaction {
        hostFragmentTransaction?.add(containerViewId, getContainerFragment(fragment), tag)
        return this
    }

    private fun getContainerFragment(pluginFragment: PluginFragment): Fragment {
        return pluginFragment.getContainerFragment().asFragment()
    }

    fun replace(containerViewId: Int, fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.replace(containerViewId, getContainerFragment(fragment))
        return this
    }

    fun replace(
        containerViewId: Int,
        fragment: PluginFragment,
        tag: String
    ): PluginFragmentTransaction {
        hostFragmentTransaction?.replace(containerViewId, getContainerFragment(fragment), tag)
        return this
    }

    fun remove(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.remove(getContainerFragment(fragment))
        return this
    }

    fun hide(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.hide(getContainerFragment(fragment))
        return this
    }

    fun show(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.show(getContainerFragment(fragment))
        return this
    }

    fun detach(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.detach(getContainerFragment(fragment))
        return this
    }

    fun attach(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.attach(getContainerFragment(fragment))
        return this
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun setPrimaryNavigationFragment(fragment: PluginFragment): PluginFragmentTransaction {
        hostFragmentTransaction?.setPrimaryNavigationFragment(getContainerFragment(fragment))
        return this
    }

    fun isEmpty(): Boolean {
        return hostFragmentTransaction?.isEmpty ?: false
    }

    fun setCustomAnimations(enter: Int, exit: Int): PluginFragmentTransaction {
        hostFragmentTransaction?.setCustomAnimations(enter, exit)
        return this
    }

    fun setCustomAnimations(
        enter: Int,
        exit: Int,
        popEnter: Int,
        popExit: Int
    ): PluginFragmentTransaction {
        hostFragmentTransaction?.setCustomAnimations(enter, exit, popEnter, popExit)
        return this
    }

    fun setTransition(transit: Int): PluginFragmentTransaction {
        hostFragmentTransaction?.setTransition(transit)
        return this
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun addSharedElement(sharedElement: View, name: String): PluginFragmentTransaction {
        hostFragmentTransaction?.addSharedElement(sharedElement, name)
        return this
    }

    fun setTransitionStyle(styleRes: Int): PluginFragmentTransaction {
        hostFragmentTransaction?.setTransitionStyle(styleRes)
        return this
    }

    fun addToBackStack(name: String): PluginFragmentTransaction {
        hostFragmentTransaction?.addToBackStack(name)
        return this
    }

    fun isAddToBackStackAllowed(): Boolean {
        return hostFragmentTransaction?.isAddToBackStackAllowed ?: false

    }

    fun disallowAddToBackStack(): PluginFragmentTransaction {
        hostFragmentTransaction?.disallowAddToBackStack()
        return this
    }

    fun setBreadCrumbTitle(res: Int): PluginFragmentTransaction {
        hostFragmentTransaction?.setBreadCrumbTitle(res)
        return this
    }

    fun setBreadCrumbTitle(text: CharSequence): PluginFragmentTransaction {
        hostFragmentTransaction?.setBreadCrumbTitle(text)
        return this
    }

    fun setBreadCrumbShortTitle(res: Int): PluginFragmentTransaction {
        hostFragmentTransaction?.setBreadCrumbShortTitle(res)
        return this
    }

    fun setBreadCrumbShortTitle(text: CharSequence): PluginFragmentTransaction {
        hostFragmentTransaction?.setBreadCrumbShortTitle(text)
        return this
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun setReorderingAllowed(reorderingAllowed: Boolean): PluginFragmentTransaction {
        hostFragmentTransaction?.setReorderingAllowed(reorderingAllowed)
        return this
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun runOnCommit(runnable: Runnable): PluginFragmentTransaction {
        hostFragmentTransaction?.runOnCommit(runnable)
        return this
    }

    fun commit(): Int {
        return hostFragmentTransaction?.commit() ?: -1
    }

    fun commitAllowingStateLoss(): Int {
        return hostFragmentTransaction?.commitAllowingStateLoss()?: -1
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun commitNow() {
        hostFragmentTransaction?.commitNow()
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun commitNowAllowingStateLoss() {
        hostFragmentTransaction?.commitNowAllowingStateLoss()
    }


}