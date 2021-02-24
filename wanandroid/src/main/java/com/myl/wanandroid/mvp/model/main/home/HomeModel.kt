package com.myl.wanandroid.mvp.model.main.home

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import io.reactivex.Observable
import javax.inject.Inject

import com.myl.wanandroid.mvp.contract.main.home.HomeContract
import com.myl.wanandroid.mvp.model.api.Api
import com.myl.wanandroid.mvp.model.entity.ApiPagerResponse
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.AriticleResponse
import com.myl.wanandroid.mvp.model.entity.BannerResponse


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class HomeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model {


    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application
    override fun getArilist(pageNo: Int): Observable<ApiResponse<ApiPagerResponse<MutableList<AriticleResponse>>>> {
       return  mRepositoryManager.obtainRetrofitService(Api::class.java)
                .getAritrilList(pageNo)
    }

    override fun getBannList(): Observable<ApiResponse<MutableList<BannerResponse>>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .getBanner()
    }

    override fun getTopArilist(): Observable<ApiResponse<MutableList<AriticleResponse>>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .getTopAritrilList()
    }

    //取消收藏
    override fun uncollect(id: Int): Observable<ApiResponse<Any>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .uncollect(id)
    }
    //收藏
    override fun collect(id: Int): Observable<ApiResponse<Any>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .collect(id)
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
