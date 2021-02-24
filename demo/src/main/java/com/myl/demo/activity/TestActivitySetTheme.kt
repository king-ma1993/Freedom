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
import android.content.Intent
import android.os.Bundle
import android.view.View

import com.myl.demo.R
import com.myl.demo.util.ToastUtil


class TestActivitySetTheme : PluginBaseActivity() {


    internal var currentTheme = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        var currentTheme = intent.getIntExtra("theme", 0)
        currentTheme++
        setTheme(if (currentTheme % 2 == 0) R.style.TestPluginTheme else R.style.PluginAppThemeLight)
        ToastUtil.showToast(
            this@TestActivitySetTheme,
            if (currentTheme % 2 == 0) "R.style.TestPluginTheme" else "R.style.PluginAppThemeLight"
        )
        //setTheme必须在super.onCreate之前调用才行
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_settheme)
        val btn = findViewById<View>(R.id.button)
        btn.isEnabled = true
        val finalCurrentTheme = currentTheme
        btn.setOnClickListener {
            btn.isEnabled = false

            val intent = Intent(this@TestActivitySetTheme, TestActivitySetTheme::class.java)
            intent.putExtra("theme", finalCurrentTheme)
            startActivity(intent)

            btn.isEnabled = true
        }
    }

}
