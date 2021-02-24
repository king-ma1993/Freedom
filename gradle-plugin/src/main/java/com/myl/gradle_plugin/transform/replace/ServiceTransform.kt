

package com.myl.gradle_plugin.transform.replace

import com.myl.gradle_plugin.transform.SimpleRenameTransform

class ServiceTransform : SimpleRenameTransform(
    mapOf(
        "android.app.Service"
                to "com.myl.androidapkplugin.pluginlib.service.PluginService"
    )
)
