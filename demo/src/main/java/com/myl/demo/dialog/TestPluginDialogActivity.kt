package com.myl.demo.dialog

import android.os.Bundle
import android.view.View
import com.myl.demo.R
import com.myl.demo.activity.PluginBaseActivity

class TestPluginDialogActivity : PluginBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_activity)
    }

    fun show(view: View) {
        val dialog = TestPluginDialog(this)
        dialog.setContentView(R.layout.layout_dialog)

        dialog.show()
    }
}