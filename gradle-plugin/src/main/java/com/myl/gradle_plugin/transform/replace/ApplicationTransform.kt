

package com.myl.gradle_plugin.transform.replace

import com.myl.gradle_plugin.transform.SimpleRenameTransform
import com.myl.gradle_plugin.transform.replace.Constants.notReplace_wrapper
import com.myl.gradle_plugin.transform.replace.Constants.replace_applicationName
import com.myl.gradle_plugin.transform.replace.Constants.replace_lifecycleCallbackName

class ApplicationTransform : SimpleRenameTransform(
    mapOf(
        "android.app.Application"
                to replace_applicationName
        ,
        "android.app.Application\$ActivityLifecycleCallbacks"
                to replace_lifecycleCallbackName
    )
) {
    override fun getWhiteList(): List<String>? {
        val appWhiteList = arrayListOf<String>()
        super.getWhiteList()?.apply {
            appWhiteList.addAll(this)
        }
        appWhiteList.add(replace_applicationName)
        appWhiteList.add(replace_lifecycleCallbackName)
//        appWhiteList.add(notReplace_wrapper)
        return appWhiteList
    }
}
