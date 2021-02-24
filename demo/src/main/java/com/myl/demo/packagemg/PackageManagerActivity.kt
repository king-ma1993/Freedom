package com.myl.demo.packagemg

import android.app.Activity
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.myl.demo.R
import kotlinx.android.synthetic.main.activity_package_manager_test.*

class PackageManagerActivity : Activity() {
    companion object {
        const val TAG = "PluginFragmentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_manager_test)
        Log.d(TAG, "oncreate")
//        val a = obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme)
//        val isAppCompat = a.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)
//        LogUtils.d(TAG, "DemoTestActivity isAppCompat:$isAppCompat")

        get_activityinfo.setOnClickListener {
            getActivityInfoTest()
        }

        get_applicationinfo.setOnClickListener {
            getApplicationInfoTest()
        }
        get_packageinfo.setOnClickListener {
            getPackageInfoTest()
        }
    }


    private fun getApplicationInfoTest() {
        try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            content.text = ("ApplicationInfo className:" + applicationInfo.className +
                    "\nnativeLibraryDir:" + applicationInfo.nativeLibraryDir
                    + "\nmetaData:" + applicationInfo.metaData)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }


    private fun getActivityInfoTest() {
        try {
            val activityInfo =
                packageManager.getActivityInfo(ComponentName(this, PackageManagerActivity::class.java), 0)
            content.text = ("activityInfo name:" + activityInfo.name
                    + "\npackageName:" + activityInfo.packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }


    private fun getPackageInfoTest() {
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            content.text = ("packageInfo versionName:" + packageInfo.versionName
                    + "\nversionCode:" + packageInfo.versionCode)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }
}