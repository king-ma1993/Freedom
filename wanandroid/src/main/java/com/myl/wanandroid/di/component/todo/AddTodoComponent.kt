package com.myl.wanandroid.di.component.todo

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.myl.wanandroid.di.module.todo.AddTodoModule

import com.jess.arms.di.scope.ActivityScope
import com.myl.wanandroid.mvp.ui.activity.todo.AddTodoActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 21:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(AddTodoModule::class), dependencies = arrayOf(AppComponent::class))
interface AddTodoComponent {
    fun inject(activity: AddTodoActivity)
}
