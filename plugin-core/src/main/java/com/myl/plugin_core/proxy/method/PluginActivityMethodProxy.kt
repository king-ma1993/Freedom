package com.myl.plugin_core.proxy.method

import com.myl.plugin_core.proxy.HostActivityInterface


/**
 * HostActivityInterface HostActivityDelegate
 * PluginActivityMethodInterface GeneratedHostActivityDelegate
 *  PluginActivityMethodProxy HostActivityDelegate
 */
interface PluginActivityMethodProxy : PluginActivityMethodInterface {

    fun setHostActivityInterface(delegator: HostActivityInterface)

    fun getPluginActivity(): Any
}