package com.myl.wanandroid.di.component.share

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.myl.wanandroid.di.module.share.ShareAriticleModule

import com.jess.arms.di.scope.ActivityScope
import com.myl.wanandroid.mvp.ui.activity.share.ShareAriticleActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/08/2019 13:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(ShareAriticleModule::class), dependencies = arrayOf(AppComponent::class))
interface ShareAriticleComponent {
    fun inject(activity: ShareAriticleActivity)
}
