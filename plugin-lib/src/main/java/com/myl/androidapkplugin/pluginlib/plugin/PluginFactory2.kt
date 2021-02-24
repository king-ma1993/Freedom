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
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub

import com.myl.androidapkplugin.pluginlib.activity.PluginActivity
import com.myl.plugin_core.loader.PluginClassLoader
import com.myl.plugin_core.util.LogUtils

import java.lang.reflect.Constructor
import java.util.HashMap

/**
 * 具备创建自定义View功能的Factory2
 *
 * // TODO #36 实现LayoutInflater 的 filter功能
 */
class PluginFactory2(
    private val factory2: LayoutInflater.Factory2,
    private val layoutInflater: LayoutInflater
) :
    LayoutInflater.Factory2 {


    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view = factory2.onCreateView(parent, name, context, attrs)
        LogUtils.d(
            TAG,
            "onCreateView name:$name view:$view context:$context attrs:$attrs" + Log.getStackTraceString(
                Throwable()
            )
        )
        if (view == null) {
            view = createPluginView(name, context, attrs)
        }
        return view
    }

    private fun createPluginView(
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var constructor: Constructor<out View>? = sConstructorMap[name]
        if (constructor != null && !verifyClassLoader(context, constructor)) {
            constructor = null
            sConstructorMap.remove(name)
        }
        var clazz: Class<out View>? = null
        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                clazz = context.classLoader.loadClass(name).asSubclass(View::class.java)

                constructor = clazz.getConstructor(*mConstructorSignature)
                constructor.isAccessible = true
                sConstructorMap[name] = constructor
            }

            val lastContext = mConstructorArgs[0]
            if (mConstructorArgs[0] == null) {
                // Fill in the context if not already within inflation.
                mConstructorArgs[0] = context
            }
            val args = mConstructorArgs
            args[1] = attrs

            val view = constructor.newInstance(*args)
            if (view is ViewStub && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // Use the same context when inflating ViewStub later.
                view.layoutInflater = layoutInflater.cloneInContext(args[0] as Context)
            }
            mConstructorArgs[0] = lastContext
            return view
        } catch (e: Exception) {
            return null
        }

    }


    private fun verifyClassLoader(context: Context, constructor: Constructor<out View>): Boolean {
        val constructorLoader = constructor.declaringClass.classLoader
        if (constructorLoader === BOOT_CLASS_LOADER) {
            // fast path for boot class loader (most common case?) - always ok
            return true
        }
        // in all normal cases (no dynamic code loading), we will exit the following loop on the
        // first iteration (i.e. when the declaring classloader is the contexts class loader).
        var cl: ClassLoader? = context.classLoader
        if (cl == PluginClassLoader::class.java && constructorLoader != cl) {
            return false
        }
        do {
            if (constructorLoader == cl) {
                return true
            }
            cl = cl!!.parent
        } while (cl != null)
        return false
    }


    private val mConstructorArgs = arrayOfNulls<Any>(2)

    private val mConstructorSignature = arrayOf(Context::class.java, AttributeSet::class.java)

    /*override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view: View?
        if (name.contains(".")) {//自定义view
            if (sCreateSystemMap[name] == null) {
                sCreateSystemMap[name] = mPartKey
            }
            view = try {
                createCustomView(name, context, attrs)
            } catch (e: Exception) {
                null
            }

        } else {
            view = if (context is PluginActivity) {//fragment的构造在activity中
                context.onCreateView(parent, name, context, attrs)
            } else {
                null
            }
        }
        return view
    }*/

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }


    //    private fun createCustomView(name: String, context: Context, attrs: AttributeSet): View? {
//        val cacheKey = mPartKey + name
//        var constructor = sConstructorMap[cacheKey]
//        if (constructor != null && !verifyClassLoader(context, constructor)) {
//            constructor = null
//            sConstructorMap.remove(cacheKey)
//        }
//        var clazz: Class<out View>? = null
//
//        try {
//            if (constructor == null) {
//                // Class not found in the cache, see if it's real, and try to add it
//                clazz = context.classLoader.loadClass(name).asSubclass(View::class.java)
//
//                constructor = clazz!!.getConstructor(*mConstructorSignature)
//                constructor.isAccessible = true
//                sConstructorMap[cacheKey] = constructor
//            }
//
//            val lastContext = mConstructorArgs[0]
//            if (mConstructorArgs[0] == null) {
//                // Fill in the context if not already within inflation.
//                mConstructorArgs[0] = context
//            }
//            val args = mConstructorArgs
//            args[1] = attrs
//
//            val view = constructor.newInstance(*args)
//            if (view is ViewStub && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                // Use the same context when inflating ViewStub later.
//                view.layoutInflater = mLayoutInflater.cloneInContext(args[0] as Context)
//            }
//            mConstructorArgs[0] = lastContext
//            return view
//        } catch (e: Exception) {
//            return null
//        }
//
//    }
//
//    private fun verifyClassLoader(context: Context, constructor: Constructor<out View>): Boolean {
//        val constructorLoader = constructor.declaringClass.classLoader
//        if (constructorLoader === BOOT_CLASS_LOADER) {
//            // fast path for boot class loader (most common case?) - always ok
//            return true
//        }
//        // in all normal cases (no dynamic code loading), we will exit the following loop on the
//        // first iteration (i.e. when the declaring classloader is the contexts class loader).
//        var cl: ClassLoader? = context.classLoader
//        do {
//            if (constructorLoader === cl) {
//                return true
//            }
//            cl = cl!!.parent
//        } while (cl != null)
//        return false
//    }
//
    companion object {

        const val TAG = "PluginFactory2"

        private val sConstructorMap = HashMap<String, Constructor<out View>>()


        private val sCreateSystemMap = HashMap<String, String>()


        private val BOOT_CLASS_LOADER = LayoutInflater::class.java.classLoader
    }


}
