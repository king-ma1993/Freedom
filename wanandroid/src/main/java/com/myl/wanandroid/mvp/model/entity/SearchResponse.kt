package com.myl.wanandroid.mvp.model.entity

import java.io.Serializable

/**
 * 搜索热词
 */
data class SearchResponse(var id: Int,
                          var link: String,
                          var name: String,
                          var order: Int,
                          var visible: Int) : Serializable
