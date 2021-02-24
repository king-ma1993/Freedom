package com.myl.plugin_core.exception

import java.lang.IllegalStateException

class PluginException(msg: String) : IllegalStateException(msg) {

    companion object {
        const val PLUGIN_INFO_IS_EMPTY = "The plugin info is empty!"
        const val UNSUPPORT_METHOD = "Unsupported Method!"
    }

}