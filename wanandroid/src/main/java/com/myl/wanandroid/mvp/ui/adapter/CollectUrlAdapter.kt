package com.myl.wanandroid.mvp.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.myl.wanandroid.R
import com.myl.wanandroid.app.weight.CollectView
import com.myl.wanandroid.mvp.model.entity.CollectUrlResponse


class CollectUrlAdapter(data: ArrayList<CollectUrlResponse>?) : BaseQuickAdapter<CollectUrlResponse, BaseViewHolder>(R.layout.item_collecturl, data) {
    private var mOnCollectViewClickListener: OnCollectViewClickListener? = null

    override fun convert(helper: BaseViewHolder, item: CollectUrlResponse?) {
        if (item != null) {
            //赋值
            item.run {
                helper.setText(R.id.item_collecturl_name, Html.fromHtml(name))
                helper.setText(R.id.item_collecturl_link, link)
                helper.getView<CollectView>(R.id.item_collecturl_collect).isChecked = true
            }
            helper.getView<CollectView>(R.id.item_collecturl_collect).setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    mOnCollectViewClickListener?.onClick(helper, v, helper.adapterPosition)
                }
            })
        }
    }

    fun setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener) {
        mOnCollectViewClickListener = onCollectViewClickListener
    }

    interface OnCollectViewClickListener {
        fun onClick(helper: BaseViewHolder, v: CollectView, position: Int)
    }
}


