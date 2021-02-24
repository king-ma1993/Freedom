package com.myl.demo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.myl.demo.util.ToastUtil

class TestPluginService : Service() {

    companion object {
        const val TAG = "TestPluginService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        ToastUtil.showToast(this, "TestPluginService onCreate")
//        LogUtils.d(TAG, "TestPluginService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ToastUtil.showToast(this, "TestPluginService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        ToastUtil.showToast(this, "TestPluginService onDestroy")
        super.onDestroy()
    }
}