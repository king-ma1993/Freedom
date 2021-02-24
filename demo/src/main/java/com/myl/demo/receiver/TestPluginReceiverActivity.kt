package com.myl.demo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.myl.demo.R
import com.myl.demo.activity.PluginBaseActivity
import com.myl.demo.util.ToastUtil
import kotlinx.android.synthetic.main.activity_receiver_test.*

class TestPluginReceiverActivity : PluginBaseActivity() {
    companion object {
        const val TAG = "TestPluginReceiverActivity"
        const val INTENT_ACTION = "com.myl.demo.receiver.action.testDynamic"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver_test)

        test_static_receiver.setOnClickListener {
            val intent = Intent(INTENT_ACTION)
            intent.putExtra("msg", "收到测试静态广播发送")
            sendBroadcast(intent)
        }

        test_dynamic_receiver.setOnClickListener {
            val intent = Intent(INTENT_ACTION)
            intent.putExtra("msg", "收到测试动态广播发送")
            sendBroadcast(intent)
        }
        registerReceiver(DynamicBroadcastReceiver(), IntentFilter(INTENT_ACTION))
    }

    private inner class DynamicBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msg = intent.getStringExtra("msg")
            ToastUtil.showToast(context, msg)
        }
    }
}