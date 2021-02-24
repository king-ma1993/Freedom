package com.myl.androidplugin

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TestService : Service() {

    companion object {
        const val TAG = "TestService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtil.showToast(this, "TestService onCreate")
//        LogUtils.d(TAG, "TestPluginService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ToastUtil.showToast(this, "TestService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
}