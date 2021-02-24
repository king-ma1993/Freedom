package com.myl.wanandroid.di.component.integral

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.myl.wanandroid.di.module.integral.IntegralModule

import com.jess.arms.di.scope.ActivityScope
import com.myl.wanandroid.mvp.ui.activity.integral.IntegralActivity
import com.myl.wanandroid.mvp.ui.activity.integral.IntegralHistoryActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 08:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(IntegralModule::class), dependencies = arrayOf(AppComponent::class))
interface IntegralComponent {
    fun inject(activity: IntegralActivity)
    fun inject(activity: IntegralHistoryActivity)
}
