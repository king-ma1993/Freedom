package com.myl.wanandroid.mvp.model.main.publicNumber

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import io.reactivex.Observable
import javax.inject.Inject

import com.myl.wanandroid.mvp.contract.main.publicNumber.PublicChildContract
import com.myl.wanandroid.mvp.model.api.Api
import com.myl.wanandroid.mvp.model.entity.ApiPagerResponse
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.AriticleResponse


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 11:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class PublicChildModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PublicChildContract.Model {
    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun getPublicDatas(pageNo: Int,cid:Int): Observable<ApiResponse<ApiPagerResponse<MutableList<AriticleResponse>>>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .getPublicNewData(pageNo,cid)
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


}
