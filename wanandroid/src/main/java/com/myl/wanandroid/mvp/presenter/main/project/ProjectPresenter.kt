package com.myl.wanandroid.mvp.presenter.main.project

import android.app.Application
import com.google.gson.Gson

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.myl.wanandroid.app.utils.CacheUtil
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.myl.wanandroid.mvp.contract.main.project.ProjectContract
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.ClassifyResponse
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 13:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class ProjectPresenter
@Inject
constructor(model: ProjectContract.Model, rootView: ProjectContract.View) :
        BasePresenter<ProjectContract.Model, ProjectContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    fun getProjectTitles() {
        val datas = CacheUtil.getProjectTitles()
        if(datas.size!=0){
            mRootView.requestTitileSucc(datas)
        }
        mModel.getTitles()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 0))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))// activity的绑定方式 使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<ApiResponse<MutableList<ClassifyResponse>>>(mErrorHandler) {
                    override fun onNext(response: ApiResponse<MutableList<ClassifyResponse>>) {
                        if (response.isSucces()) {
                            CacheUtil.setProjectTitles(Gson().toJson(response.data))
                            if(datas.size==0){
                                mRootView.requestTitileSucc(response.data)
                            }
                        } else {
                            if(datas.size==0){
                                mRootView.requestTitileSucc(datas)
                            }
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        if(datas.size==0){
                            mRootView.requestTitileSucc(datas)
                        }
                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
