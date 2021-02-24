package com.myl.wanandroid.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.myl.wanandroid.R
import com.myl.wanandroid.mvp.model.entity.OpenProject

/**
 * 开源项目 adapter
 * @Author:         hegaojian
 * @CreateDate:     2019/9/1 9:52
 */
class OpenProjectAdapter(data: ArrayList<OpenProject>?) : BaseQuickAdapter<OpenProject, BaseViewHolder>(R.layout.item_openproject, data) {

    override fun convert(helper: BaseViewHolder?, item: OpenProject?) {
        helper?:return
        //赋值
        item?.run {
            helper.setText(R.id.item_openproject_name, name)
            helper.setText(R.id.item_openproject_content, content)


        }
    }
}


