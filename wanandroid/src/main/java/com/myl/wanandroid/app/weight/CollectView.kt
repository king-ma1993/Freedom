package com.myl.wanandroid.app.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils
import com.myl.wanandroid.R

import com.myl.wanandroid.app.utils.CacheUtil
import com.myl.wanandroid.mvp.ui.activity.start.LoginActivity
import per.goweii.reveallayout.RevealLayout

/**
 * @author CuiZhen
 */
class CollectView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RevealLayout(context, attrs, defStyleAttr), View.OnTouchListener {

    private var onCollectViewClickListener: OnCollectViewClickListener? = null

    override fun initAttr(attrs: AttributeSet) {
        super.initAttr(attrs)
        setCheckWithExpand(true)
        setUncheckWithExpand(false)
        setCheckedLayoutId(R.layout.layout_collect_view_checked)
        setUncheckedLayoutId(R.layout.layout_collect_view_unchecked)
        setAnimDuration(500)
        setAllowRevert(true)
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> if (CacheUtil.isLogin()) {
                onCollectViewClickListener?.onClick(this)
            } else {
                ArmsUtils.startActivity(AppManager.getAppManager().currentActivity,LoginActivity::class.java)
                return true
            }
            else -> {
            }
        }
        return false
    }

    fun setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener) {
        this.onCollectViewClickListener = onCollectViewClickListener
    }

    interface OnCollectViewClickListener {
        fun onClick(v: CollectView)
    }
}