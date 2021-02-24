package com.myl.wanandroid.di.module.main.project

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.myl.wanandroid.mvp.contract.main.project.ProjectContract
import com.myl.wanandroid.mvp.model.main.project.ProjectModel


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
@Module
//构建ProjectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ProjectModule(private val view: ProjectContract.View) {
    @FragmentScope
    @Provides
    fun provideProjectView(): ProjectContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideProjectModel(model: ProjectModel): ProjectContract.Model {
        return model
    }
}
