package com.myl.wanandroid.di.component.main.project

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.myl.wanandroid.di.module.main.project.ProjectChildModule

import com.jess.arms.di.scope.FragmentScope
import com.myl.wanandroid.mvp.ui.activity.main.project.ProjectChildFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 17:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = arrayOf(ProjectChildModule::class), dependencies = arrayOf(AppComponent::class))
interface ProjectChildComponent {
    fun inject(fragment: ProjectChildFragment)
}
