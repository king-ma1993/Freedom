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

package com.myl.plugin_core.proxy


import java.util.HashMap

/**
 * DelegateProvider依赖注入类
 *
 *
 * dynamic-pluginloader通过这个类实现将PluginLoader中的DelegateProvider实现注入到plugincontainer中。
 *
 */
object DelegateProviderFactory {
    const val TAG = "DelegateProviderFactory"
    val DEFAULT_KEY = "DEFAULT_KEY"
    private val delegateProviderMap = HashMap<String, DelegateProvider>()


    fun setDelegateProvider(key: String, delegateProvider: DelegateProvider) {
//        LogUtils.d(TAG, "setDelegateProvider:$delegateProvider hascode:${hashCode()} pid:${getPid()}")
        delegateProviderMap[key] = delegateProvider
    }

    fun getDelegateProvider(key: String): DelegateProvider? {
//        LogUtils.d(TAG, "getDelegateProvider hascode:${hashCode()} pid:${getPid()}")
        return delegateProviderMap[key]
    }

    fun getDelegateProviderKey(): String {
        return DEFAULT_KEY
    }

    private fun getPid(): Int {
        return android.os.Process.myPid()
    }
}
