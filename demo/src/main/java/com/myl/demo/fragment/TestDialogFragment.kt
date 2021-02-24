package com.myl.demo.fragment

import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myl.demo.R

class TestDialogFragment : DialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog, container, false)
    }

    companion object {
        const val TAG = "TestDialogFragment"
        fun showTestDialogFragment(fragmentManager: FragmentManager) {
            TestDialogFragment().show(fragmentManager, TAG)
        }
    }
}