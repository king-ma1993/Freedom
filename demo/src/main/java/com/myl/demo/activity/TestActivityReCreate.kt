/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.myl.demo.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.myl.demo.R
import com.myl.demo.util.ToastUtil


class TestActivityReCreate : PluginBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recreate)
        val textView = findViewById<TextView>(R.id.tv_msg)
        val isRecreate = intent.getBooleanExtra("reCreate", false)
        textView.text = "isRecreate:$isRecreate"
        ToastUtil.showToast(this, "onCreate")
    }

    fun reCreate(view: View) {
        intent.putExtra("reCreate", true)
        recreate()
    }

    override fun onStart() {
        super.onStart()
        ToastUtil.showToast(this, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        ToastUtil.showToast(this, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        ToastUtil.showToast(this, "onResume")
    }

    override fun onPause() {
        super.onPause()
        ToastUtil.showToast(this, "onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        ToastUtil.showToast(this, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ToastUtil.showToast(this, "onRestoreInstanceState")
    }

    override fun onStop() {
        super.onStop()
        ToastUtil.showToast(this, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        ToastUtil.showToast(this, "onDestroy")
    }
}
