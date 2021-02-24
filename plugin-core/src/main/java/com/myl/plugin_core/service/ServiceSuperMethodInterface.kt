package com.myl.plugin_core.service

import android.app.Notification

@Deprecated("废弃")
interface ServiceSuperMethodInterface {

    fun superStartForeground(id: Int, notification: Notification)

    fun superStopForeground(removeNotification: Boolean)

    fun superStopForeground(flags: Int)

    fun superStopSelf()


    fun superStopSelf(startId: Int)

    fun superStopSelfResult(startId: Int): Boolean

}