package com.myl.wanandroid.mvp.model.entity

import java.io.Serializable

data class ShareResponse(var coinInfo: CoinInfo,
                         var shareArticles: ApiPagerResponse<MutableList<AriticleResponse>>):Serializable