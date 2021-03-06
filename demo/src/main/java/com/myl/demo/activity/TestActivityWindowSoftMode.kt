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
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView

import com.myl.demo.R


class TestActivityWindowSoftMode : PluginBaseActivity() {


    private var mEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_softmode)

        val layoutParams = window.attributes
        val is_state_visible =
            layoutParams.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE

        val textView = findViewById<TextView>(R.id.result)
        textView.text = "SOFT_INPUT_STATE_VISIBLE:$is_state_visible"

        mEditText = findViewById(R.id.edit_view)
        mEditText!!.requestFocus()


    }


}
