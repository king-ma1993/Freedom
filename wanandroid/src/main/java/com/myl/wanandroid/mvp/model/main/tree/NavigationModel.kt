package com.myl.wanandroid.mvp.model.main.tree

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import io.reactivex.Observable
import javax.inject.Inject

import com.myl.wanandroid.mvp.contract.main.tree.NavigationContract
import com.myl.wanandroid.mvp.model.api.Api
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.NavigationResponse


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 11:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class NavigationModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), NavigationContract.Model {
    override fun getNavigationData(): Observable<ApiResponse<MutableList<NavigationResponse>>> {
        return mRepositoryManager
                .obtainRetrofitService(Api::class.java)
                .getNavigationData()
    }

    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
        super.onDestroy()
    }
}
