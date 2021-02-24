package com.myl.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.myl.demo.activity.PluginBaseActivity
import com.myl.demo.dialog.TestPluginDialogActivity
import com.myl.demo.fragment.PluginFragmentActivity
import com.myl.demo.packagemg.PackageManagerActivity
import com.myl.demo.receiver.TestPluginReceiverActivity
import com.myl.demo.service.TestPluginService
import com.myl.demo.service.TestServiceActivity
import kotlinx.android.synthetic.main.activity_plugin_demo.*

class PluginDemoActivity : PluginBaseActivity() {

    companion object {
        const val TAG = "PluginDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "oncreate")
        val view = layoutInflater?.inflate(R.layout.activity_plugin_demo, null)
        setContentView(view)
        activity_test.setOnClickListener {
            val intent = Intent(this, DemoTestActivity::class.java)
            startActivity(intent)
        }
        val classLoader = classLoader
        Log.d(TAG, "classLoader:$classLoader layoutInflater.context:${layoutInflater.context}")
        val button = classLoader.loadClass("android.widget.Button")
        fragment_test.setOnClickListener {
            val intent = Intent(this, PluginFragmentActivity::class.java)
            startActivity(intent)
        }
        broadcast_test.setOnClickListener {
            val intent = Intent(this, TestPluginReceiverActivity::class.java)
            startActivity(intent)
        }
        dialog_test.setOnClickListener {
            val intent = Intent(this, TestPluginDialogActivity::class.java)
            startActivity(intent)
        }
        service_test.setOnClickListener {
            val intent = Intent(this, TestServiceActivity::class.java)
            startActivity(intent)
        }
        packagemanger_test.setOnClickListener {
            val intent = Intent(this, PackageManagerActivity::class.java)
            startActivity(intent)
        }
    }


//    private fun startIntentActivity(className: Class<T>) {
//        val intent = Intent(this, className)
//        startActivity(intent)
//    }

    override fun onStart() {
        super.onStart()
        Log.d("myl", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("myl", "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("myl", "onDestroy")
    }
}
