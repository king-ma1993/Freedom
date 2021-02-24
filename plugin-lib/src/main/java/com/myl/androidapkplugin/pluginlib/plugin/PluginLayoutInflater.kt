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

package com.myl.androidapkplugin.pluginlib.plugin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import com.myl.plugin_core.activity.PluginContainerActivity
import com.myl.plugin_core.util.LogUtils

/**
 * 在HostActivityDelegate.getLayoutInflater返回的LayoutInflater虽然已经被替换为ShadowActivity作为Context了.
 * 但是Fragment在创建时还是会通过这个LayoutInflater的cloneInContext方法,传入宿主Activity作为新的Context.
 * 这里通过覆盖cloneInContext方法,避免Context被替换.
 * 见onGetLayoutInflater() of Activity$HostCallbacks in Activity.java
 */
class PluginLayoutInflater : LayoutInflater {

    private val sClassPrefixList = arrayOf("android.widget.", "android.webkit.", "android.app.")

    constructor(context: Context) : super(context) {}

    constructor(original: LayoutInflater, newContext: Context) : super(original, newContext) {}

    override fun cloneInContext(newContext: Context): LayoutInflater? {
        return PluginLayoutInflater(this, newContext)
//        return createNewContextLayoutInflater(newContext)
    }

    private fun createNewContextLayoutInflater(newContext: Context): LayoutInflater {
        return if (newContext is PluginContainerActivity) {
            val pluginContainerActivity = newContext as PluginContainerActivity
            val pluginActivity = pluginContainerActivity.getPluginActivity()
            PluginLayoutInflater(this, pluginActivity as Context)
        } else {
            //context有2种可能，1种是PluginContext,一种是其他context
            PluginLayoutInflater(this, newContext)
        }
    }

    override fun onCreateView(name: String?, attrs: AttributeSet?): View {
        LogUtils.d(
            TAG,
            "onCreateView name:$name " + Log.getStackTraceString(Throwable())
        )
        for (prefix in sClassPrefixList) {
            try {
                val view = createView(name, prefix, attrs)
                if (view != null) {
                    return view
                }
            } catch (e: ClassNotFoundException) {
                // In this case we want to let the base class take a crack
                // at it.
            }

        }
        return super.onCreateView(name, attrs)
    }


    override fun setFactory2(factory: Factory2?) {
        factory?.apply {
            val pluginFactory2 = PluginFactory2(factory, this@PluginLayoutInflater)
            super.setFactory2(pluginFactory2)
            return
        }
        super.setFactory2(factory)
    }

    companion object {

        const val TAG = "PluginLayoutInflater"

        fun build(
            original: LayoutInflater,
            newContext: Context
        ): PluginLayoutInflater {
            return PluginLayoutInflater(original, newContext)
        }

//        private val sClassPrefixList = arrayOf("android.widget.", "android.webkit.", "android.app.")
    }

    //    @Override
    //    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
    //        for (String prefix : sClassPrefixList) {
    //            try {
    //                Pair<String,String> result = changeViewNameAndPrefix(prefix, name);
    //                View view = createView(result.first, result.second, attrs);
    //                if (view != null) {
    //                    return view;
    //                }
    //            } catch (ClassNotFoundException e) {
    //                // In this case we want to let the base class take a crack
    //                // at it.
    //            }
    //        }
    //
    //        return super.onCreateView(name, attrs);
    //    }
    //
    //    @Override
    //    public LayoutInflater cloneInContext(Context newContext) {
    //        return createNewContextLayoutInflater(newContext);
    //    }
    //
    //    abstract LayoutInflater createNewContextLayoutInflater(Context context);
    //
    //    abstract Pair<String,String> changeViewNameAndPrefix(String prefix, String name);

}
