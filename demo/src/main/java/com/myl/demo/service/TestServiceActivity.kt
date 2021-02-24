package com.myl.demo.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.myl.androidapkplugin.pluginlib.core.PluginLibManager
import com.myl.demo.R
import com.myl.demo.util.LogUtils
import com.myl.demo.util.ToastUtil
import kotlinx.android.synthetic.main.activity_fragment_test.*
import kotlinx.android.synthetic.main.activity_service_test.*

class TestServiceActivity : Activity() {
    companion object {
        const val TAG = "PluginFragmentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_test)
        Log.d(TAG, "oncreate")
//        val a = obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme)
//        val isAppCompat = a.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)
//        LogUtils.d(TAG, "DemoTestActivity isAppCompat:$isAppCompat")

        start_service.setOnClickListener {
            val intent = Intent(this, TestPluginService::class.java)
            startService(intent)
        }

        stop_service.setOnClickListener {
            val intent = Intent(this, TestPluginService::class.java)
            stopService(intent)
        }

        val serviceConnection = object : ServiceConnection {

            override fun onServiceDisconnected(name: ComponentName?) {
                ToastUtil.showToast(
                    this@TestServiceActivity,
                    "bindPluginService onServiceDisconnected"
                )
                LogUtils.d(TAG, "bindPluginService onServiceDisconnected")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                ToastUtil.showToast(
                    this@TestServiceActivity,
                    "bindPluginService onServiceConnected"
                )
                LogUtils.d(TAG, "bindPluginService onServiceConnected")
            }
        }

        bind_service.setOnClickListener {
            val intent = Intent(this, TestPluginService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        unbind_service.setOnClickListener {
            unbindService(serviceConnection)
        }

    }
}