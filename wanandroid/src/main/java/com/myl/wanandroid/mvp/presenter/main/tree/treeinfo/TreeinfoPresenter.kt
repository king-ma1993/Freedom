package com.myl.wanandroid.mvp.presenter.main.tree.treeinfo

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.myl.wanandroid.app.utils.HttpUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.myl.wanandroid.mvp.contract.main.tree.treeinfo.TreeinfoContract
import com.myl.wanandroid.mvp.model.entity.ApiPagerResponse
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.AriticleResponse
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/23/2019 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class TreeinfoPresenter
@Inject
constructor(model: TreeinfoContract.Model, rootView: TreeinfoContract.View) :
        BasePresenter<TreeinfoContract.Model, TreeinfoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    /**
     * 获取体系下的数据
     */
    fun getTreeInfoDataByType(pageNo: Int, cid: Int) {
        mModel.getTreeInfoDatas(pageNo, cid)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 0))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))//fragment的绑定方式  使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<ApiResponse<ApiPagerResponse<MutableList<AriticleResponse>>>>(mErrorHandler) {
                    override fun onNext(response: ApiResponse<ApiPagerResponse<MutableList<AriticleResponse>>>) {
                        if (response.isSucces()) {
                            mRootView.requestDataSucc(response.data)
                        } else {
                            mRootView.requestDataFaild(response.errorMsg)
                        }
                    }
                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.requestDataFaild(HttpUtils.getErrorText(t))
                    }
                })
    }

    /**
     * 收藏
     */
    fun collect(id:Int,position:Int) {
        mModel.collect(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 0))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView,FragmentEvent.DESTROY))//fragment的绑定方式  使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<ApiResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: ApiResponse<Any>) {
                        if (response.isSucces()) {
                            //收藏成功
                            mRootView.collect(true,position)
                        }else{
                            //收藏失败
                            mRootView.collect(false,position)
                            mRootView.showMessage(response.errorMsg)
                        }
                    }
                    override fun onError(t: Throwable) {
                        super.onError(t)
                        //收藏失败
                        mRootView.collect(false,position)
                        mRootView.showMessage(HttpUtils.getErrorText(t))
                    }
                })
    }

    /**
     * 取消收藏
     */
    fun uncollect(id:Int,position:Int) {
        mModel.uncollect(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(1, 0))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView,FragmentEvent.DESTROY))//fragment的绑定方式  使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<ApiResponse<Any>>(mErrorHandler) {
                    override fun onNext(response: ApiResponse<Any>) {
                        if (response.isSucces()) {
                            //取消收藏成功
                            mRootView.collect(false,position)
                        }else{
                            //取消收藏失败
                            mRootView.collect(true,position)
                            mRootView.showMessage(response.errorMsg)
                        }
                    }
                    override fun onError(t: Throwable) {
                        super.onError(t)
                        //取消收藏失败
                        mRootView.collect(true,position)
                        mRootView.showMessage(HttpUtils.getErrorText(t))
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
