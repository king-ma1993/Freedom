
package com.myl.gradle_plugin.transform.replace

import com.myl.gradle_plugin.transform.SimpleRenameTransform
import com.myl.gradle_plugin.transform.replace.Constants.replace_activityName

class ActivityTransform : SimpleRenameTransform(
    mapOf(
        "android.app.Activity"
                to replace_activityName
    )
) {
    override fun getWhiteList(): List<String>? {
        val appWhiteList = arrayListOf<String>()
        super.getWhiteList()?.apply {
            appWhiteList.addAll(this)
        }
        appWhiteList.add(Constants.pluginActivityDelegateProxy)
        return appWhiteList

    }
}
