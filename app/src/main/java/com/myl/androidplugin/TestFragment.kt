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

package com.myl.androidplugin

import android.app.Fragment
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView



class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.layout_fragment_test, null, false)
        val textView = view?.findViewById<TextView>(R.id.tv_msg)
        val bundle = arguments
        if (bundle != null) {
            val msg = bundle.getString("msg")
            if (!TextUtils.isEmpty(msg)) {
                textView?.text = msg
            }
        }
        return view
    }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle
//    ): View? {
//        val view = inflater.inflate(R.layout.layout_fragment_test, null, false)
//        val textView = view.findViewById<TextView>(R.id.tv_msg)
//        val bundle = arguments
//        if (bundle != null) {
//            val msg = bundle.getString("msg")
//            if (!TextUtils.isEmpty(msg)) {
//                textView.text = msg
//            }
//        }
//        return view
//    }

    companion object {

        fun newInstance(bundle: Bundle): TestFragment {
            val testFragment = TestFragment()
            testFragment.arguments = bundle
            return testFragment
        }
    }
}
