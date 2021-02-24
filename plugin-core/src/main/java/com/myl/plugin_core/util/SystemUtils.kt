package com.myl.plugin_core.util

import java.util.regex.Pattern

object SystemUtils {

    const val TAG = "SystemUtils"

    private val VM_WITH_ART_VERSION_MAJOR = 2
    private val VM_WITH_ART_VERSION_MINOR = 1

    fun isVmArt(): Boolean {
        var isArt = false
        val versionString = System.getProperty("java.vm.version")
        if (versionString != null) {
            val matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString!!)
            if (matcher.matches()) {
                try {
                    val major = Integer.parseInt(matcher.group(1))
                    val minor = Integer.parseInt(matcher.group(2))
                    isArt =
                        major > VM_WITH_ART_VERSION_MAJOR || major == VM_WITH_ART_VERSION_MAJOR && minor >= VM_WITH_ART_VERSION_MINOR
                } catch (e: NumberFormatException) {
                    LogUtils.w(TAG, "error parse: $versionString")
                }

            }
        }

        LogUtils.i(
            TAG,
            "VM with version: $versionString" + if (isArt)
                " has ART support"
            else
                " does not have ART support"
        )
        return isArt
    }
}