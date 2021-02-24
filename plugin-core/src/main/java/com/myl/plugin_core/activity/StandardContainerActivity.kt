package com.myl.plugin_core.activity

import com.myl.plugin_core.util.LogUtils

/**
 *
 */
class StandardContainerActivity : PluginContainerActivity() {
    companion object {
        const val TAG = "StandardContainerActivity"
    }

    init {
        LogUtils.d(TAG, "StandardContainerActivity init")
    }

}