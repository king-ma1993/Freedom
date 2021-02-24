package com.myl.demo.activity

import android.app.Activity

open class PluginBaseActivity: Activity() {

    override fun onBackPressed() {
        super.onBackPressed()
//        finish()
    }
}