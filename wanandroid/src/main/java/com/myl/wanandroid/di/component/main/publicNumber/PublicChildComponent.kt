package com.myl.wanandroid.di.component.main.publicNumber

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.myl.wanandroid.di.module.main.publicNumber.PublicChildModule

import com.jess.arms.di.scope.FragmentScope
import com.myl.wanandroid.mvp.ui.activity.main.publicNumber.PublicChildFragment


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
@Component(modules = arrayOf(PublicChildModule::class), dependencies = arrayOf(AppComponent::class))
interface PublicChildComponent {
    fun inject(fragment: PublicChildFragment)
}
