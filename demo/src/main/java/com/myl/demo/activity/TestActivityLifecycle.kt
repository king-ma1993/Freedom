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

import android.os.Bundle

import com.myl.demo.R
import com.myl.demo.util.LogUtils
import com.myl.demo.util.ToastUtil

class TestActivityLifecycle : PluginBaseActivity() {

    companion object {
        const val TAG = "TestActivityLifecycle"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_lifecycle)
        LogUtils.d(TAG, "onCreate")
        ToastUtil.showToast(this, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "onStart")
        ToastUtil.showToast(this, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "onRestart")
        ToastUtil.showToast(this, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "onResume")
        ToastUtil.showToast(this, "onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.d(TAG, "onSaveInstanceState")
        ToastUtil.showToast(this, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.d(TAG, "onRestoreInstanceState")
        ToastUtil.showToast(this, "onRestoreInstanceState")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "onStop")
        ToastUtil.showToast(this, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "onDestroy")
        ToastUtil.showToast(this, "onDestroy")
    }
}
