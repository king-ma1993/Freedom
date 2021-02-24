package com.myl.androidapkplugin.pluginlib.fragment

import android.annotation.TargetApi
import android.app.FragmentManager
import android.os.Build
import android.os.Bundle
import java.util.*

class PluginFragmentManager(var hostFragmentManager: FragmentManager?) {

    fun beginTransaction(): PluginFragmentTransaction {
        return PluginFragmentTransaction(
            hostFragmentManager?.beginTransaction()
        )
    }

    fun findFragmentByTag(tag: String): PluginFragment? {
        val fragmentByTag = hostFragmentManager?.findFragmentByTag(tag)
        return if (fragmentByTag is IContainerFragment) {
            (fragmentByTag as IContainerFragment).pluginFragment
        } else {
            null
        }
    }

    fun executePendingTransactions(): Boolean {
        return hostFragmentManager?.executePendingTransactions() ?: false
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun getFragments(): List<PluginFragment> {
        val containerFragments = hostFragmentManager?.fragments
        val pluginFragments = ArrayList<PluginFragment>()
        if (containerFragments != null && containerFragments!!.size > 0) {
            for (containerFragment in containerFragments!!) {
                if (containerFragment is IContainerFragment) {
                    pluginFragments.add((containerFragment as IContainerFragment).pluginFragment)
                }
            }
        }
        return if (pluginFragments.size > 0) pluginFragments else Collections.EMPTY_LIST as List<PluginFragment>
    }

    fun getFragment(bundle: Bundle, key: String): PluginFragment {
        val fragment = hostFragmentManager?.getFragment(bundle, key)
        return (fragment as IContainerFragment).pluginFragment
    }
}