/*
 * Copyright (C) 2017-2018 Manbang Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myl.plugin_core.manifest

import android.content.IntentFilter
import android.content.res.AssetManager
import android.content.res.XmlResourceParser
import android.os.PatternMatcher
import android.text.TextUtils
import androidx.collection.ArrayMap
import androidx.collection.ArraySet


import com.myl.plugin_core.exception.PluginException
import com.myl.plugin_core.util.LogUtils

import org.xmlpull.v1.XmlPullParserException

import java.io.IOException
import java.util.ArrayList

/**
 * 插件 APK AndroidManifest.xml 解析器
 */

object AndroidManifestParser {

    private val NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    private val ACTION_MAIN = "android.intent.action.MAIN"
    private val CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER"
    private val MANIFEST = "manifest"
    private val ACTIVITY = "activity"
    private val SERVICE = "service"
    private val RECEIVER = "receiver"
    private val INTENT_FILTER = "intent-filter"
    private val ACTION = "action"
    private val CATEGORY = "category"
    private val DATA = "data"
    private val TAG = "AndroidManifestParser"


    private fun getFullClassName(pkgName: String?, className: String?): String? {
        if (className == null) {
            return null
        }

        return if (className.startsWith(".")) {
            pkgName!! + className
        } else if (!className.contains(".")) {
            "$pkgName.$className"
        } else {
            className
        }
    }

    @Throws(PluginException::class)
    fun parse(assetManager: AssetManager): ComponentIntentFilters {
        var curName: String? = null
        var curFilter: IntentFilter? = null
        var curFilters: MutableList<IntentFilter>? = null
        var curActions: MutableList<String>? = null
        var curCategories: MutableList<String>? = null
        var curDataElements: MutableList<DataElement>? = null

        var packageName: String? = null
        val componentIntentFilters = ComponentIntentFilters()
        var parser: XmlResourceParser? = null
        try {
            parser = assetManager.openXmlResourceParser("AndroidManifest.xml")
            var eventType = parser.next()
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                when (eventType) {
                    XmlResourceParser.START_TAG -> {
                        val name = parser.name
                        when (name) {
                            MANIFEST -> packageName = parser.getAttributeValue(null, "package")
                            ACTIVITY -> {
                                curName = getFullClassName(packageName, getNameAttrValue(parser))
                                curFilters = ArrayList()
                            }
                            SERVICE -> {
                                curName = getFullClassName(packageName, getNameAttrValue(parser))
                                curFilters = ArrayList()
                            }
                            RECEIVER -> {
                                curName = getFullClassName(packageName, getNameAttrValue(parser))
                                curFilters = ArrayList()
                            }
                            INTENT_FILTER -> if (curFilters != null) {
                                curFilter = IntentFilter()
                                curFilters.add(curFilter)
                            }
                            ACTION -> {
                                if (curActions == null) {
                                    curActions = ArrayList()
                                }
                                curActions.add(getNameAttrValue(parser))
                            }
                            CATEGORY -> {
                                if (curCategories == null) {
                                    curCategories = ArrayList()
                                }
                                curCategories.add(getNameAttrValue(parser))
                            }

                            DATA -> {
                                if (curDataElements == null) {
                                    curDataElements = ArrayList()
                                }
                                val data = DataElement()
                                data.scheme = parser.getAttributeValue(NAMESPACE_ANDROID, "scheme")
                                data.mimeType =
                                    parser.getAttributeValue(NAMESPACE_ANDROID, "mimeType")
                                data.host = parser.getAttributeValue(NAMESPACE_ANDROID, "host")
                                data.port = parser.getAttributeValue(NAMESPACE_ANDROID, "port")
                                data.path = parser.getAttributeValue(NAMESPACE_ANDROID, "path")
                                data.pathPattern =
                                    parser.getAttributeValue(NAMESPACE_ANDROID, "pathPattern")
                                data.pathPrefix =
                                    parser.getAttributeValue(NAMESPACE_ANDROID, "pathPrefix")

                                curDataElements.add(data)
                            }
                            else -> {
                            }
                        }
                    }
                    XmlResourceParser.END_TAG -> {
                        val name = parser.name
                        when (name) {
                            INTENT_FILTER -> {
                                if (curActions != null) {
                                    for (action in curActions) {
                                        curFilter!!.addAction(action)
                                    }
                                }
                                if (curCategories != null) {
                                    for (cate in curCategories) {
                                        curFilter!!.addCategory(cate)
                                    }
                                }

                                if (curDataElements != null) {
                                    for (bean in curDataElements) {
                                        if (!TextUtils.isEmpty(bean.scheme)) {
                                            curFilter!!.addDataScheme(bean.scheme)
                                        }

                                        if (!TextUtils.isEmpty(bean.host) && !TextUtils.isEmpty(bean.port)) {
                                            curFilter!!.addDataAuthority(bean.host, bean.port)
                                        }

                                        if (!TextUtils.isEmpty(bean.path)) {
                                            curFilter!!.addDataPath(
                                                bean.path,
                                                bean.patternMatcherType
                                            )
                                        }

                                        try {
                                            if (!TextUtils.isEmpty(bean.mimeType)) {
                                                curFilter!!.addDataType(bean.mimeType)
                                            }
                                        } catch (e: IntentFilter.MalformedMimeTypeException) {
                                            LogUtils.w(TAG, "invalid mime type: " + bean.mimeType!!)
                                        }

                                    }
                                }

                                curActions = null
                                curCategories = null
                                curDataElements = null
                            }
                            ACTIVITY -> if (!TextUtils.isEmpty(curName) && curFilters != null) {
                                componentIntentFilters.mActivities[curName!!] = curFilters
                                for (filter in curFilters) {
                                    if (filter.hasAction(ACTION_MAIN) && filter.hasCategory(
                                            CATEGORY_LAUNCHER
                                        )
                                    ) {
                                        componentIntentFilters.mLauncherActivities.add(curName)
                                    }
                                }
                            }
                            SERVICE -> if (!TextUtils.isEmpty(curName) && curFilters != null) {
                                componentIntentFilters.mServices[curName!!] = curFilters
                            }
                            RECEIVER -> if (!TextUtils.isEmpty(curName) && curFilters != null) {
                                componentIntentFilters.mReceivers[curName!!] = curFilters
                            }
                            else -> {
                            }
                        }
                    }

                    else -> {
                    }
                }
                eventType = parser.next()
            }
            return componentIntentFilters
        } catch (e: IOException) {
            throw PluginException("open AndroidManifest.xml failure:$e")
        } catch (e: XmlPullParserException) {
            throw PluginException("parse AndroidManifest.xml failure:$e")
        } finally {
            parser?.close()
        }
    }

    // 获取 android:name 属性的值
    private fun getNameAttrValue(parser: XmlResourceParser): String {
        return parser.getAttributeValue(NAMESPACE_ANDROID, "name")
    }

    class ComponentIntentFilters {
        /**
         * All Activities
         */
        var mActivities: MutableMap<String, List<IntentFilter>> = ArrayMap()

        /**
         * All Services
         */
        var mServices: MutableMap<String, List<IntentFilter>> = ArrayMap()

        /**
         * All Receivers
         */
        var mReceivers: MutableMap<String, List<IntentFilter>> = HashMap()

        /**
         * Launcher Activities
         */
        var mLauncherActivities: MutableSet<String> = ArraySet()
    }

    private class DataElement {

        var scheme: String? = null
        var host: String? = null
        var port: String? = null
        var mimeType: String? = null
        var path: String? = null
        var pathPattern: String? = null
        var pathPrefix: String? = null

        /**
         * 获得 path 匹配类型
         */
        internal val patternMatcherType: Int
            get() = if (TextUtils.isEmpty(pathPattern) && TextUtils.isEmpty(pathPattern)) {
                PatternMatcher.PATTERN_LITERAL
            } else if (!TextUtils.isEmpty(pathPrefix)) {
                PatternMatcher.PATTERN_PREFIX
            } else {
                PatternMatcher.PATTERN_SIMPLE_GLOB
            }

        override fun toString(): String {
            return String.format(
                "{scheme:%s, host:%s, mimeType:%s, path:%s, pathPattern:%s, pathPrefix:%s, port:%s}",
                scheme,
                host,
                mimeType,
                pathPattern,
                pathPrefix,
                path,
                port
            )
        }
    }

}// prevent instantiate
