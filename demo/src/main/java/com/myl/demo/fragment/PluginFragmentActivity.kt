package com.myl.demo.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.myl.demo.R
import com.myl.demo.util.LogUtils
import kotlinx.android.synthetic.main.activity_fragment_test.*

class PluginFragmentActivity : Activity() {
    companion object {
        const val TAG = "PluginFragmentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        Log.d(TAG, "oncreate")
//        val a = obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme)
//        val isAppCompat = a.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)
//        LogUtils.d(TAG, "DemoTestActivity isAppCompat:$isAppCompat")

        test_add_fragment.setOnClickListener {
            val intent = Intent(this, TestPluginFragmentActivity::class.java)
            startActivity(intent)
        }

        test_xml_fragment.setOnClickListener {
            val intent = Intent(this, TestXmlFragmentActivity::class.java)
            startActivity(intent)
        }
        dialog_fragment.setOnClickListener {
            val intent = Intent(this, TestDialogFragmentActivity::class.java)
            startActivity(intent)
        }
    }
}