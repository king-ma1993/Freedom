package com.myl.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.myl.demo.activity.*
import com.myl.demo.util.LogUtils
import kotlinx.android.synthetic.main.activity_test.*

class DemoTestActivity : Activity() {
    companion object {
        const val TAG = "DemoTestActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Log.d(TAG, "oncreate")
//        val a = obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme)
//        val isAppCompat = a.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)
//        LogUtils.d(TAG, "DemoTestActivity isAppCompat:$isAppCompat")

        test_add_fragment.setOnClickListener {
            val intent = Intent(this, TestActivityLifecycle::class.java)
            startActivity(intent)
        }

        test_xml_fragment.setOnClickListener {
            val intent = Intent(this, TestActivityOptionMenu::class.java)
            startActivity(intent)
        }

        test_orientation.setOnClickListener {
            val intent = Intent(this, TestActivityOrientation::class.java)
            startActivity(intent)
        }

        test_recreate.setOnClickListener {
            val intent = Intent(this, TestActivityReCreate::class.java)
            startActivity(intent)
        }

        test_recreatebysystem.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", "https://www.baidu.com")
            val intent = Intent(this, TestActivityBundle::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        test_activity_theme.setOnClickListener {
            val intent = Intent(this, TestActivitySetTheme::class.java)
            startActivity(intent)
        }

        test_windowSoftInputMode.setOnClickListener {
            val intent = Intent(this, TestActivityWindowSoftMode::class.java)
            startActivity(intent)
        }

        test_appcompatactivity.setOnClickListener {
            val intent = Intent(this, TestAppCompatActivityOnCreate::class.java)
            startActivity(intent)
        }

        test_view.setOnClickListener {
            val intent = Intent(this, TestViewActivity::class.java)
            startActivity(intent)
        }
    }
}