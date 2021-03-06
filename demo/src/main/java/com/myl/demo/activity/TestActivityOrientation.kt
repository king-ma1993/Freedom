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
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.myl.demo.R
import com.myl.demo.util.LogUtils
import com.myl.demo.util.ToastUtil
import kotlinx.android.synthetic.main.layout_orientation.*


class TestActivityOrientation : PluginBaseActivity() {

    companion object {
        const val TAG = "TestActivityOrientation"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_orientation)
        ToastUtil.showToast(this, "onCreate")
        val button = findViewById<Button>(R.id.button)
        LogUtils.d(TAG, "TestActivityOrientation:button context:${button.context}")
        LogUtils.d(TAG, "TestActivityOrientation:layout inflater context:${layoutInflater.context}")
//        button.setOnClickListener {
//            setOrientation(it)
//        }
    }


    fun setOrientation(view: View) {
        val orientation = requestedOrientation
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}
