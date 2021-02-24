package com.myl.wanandroid.di.module.main.home

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.myl.wanandroid.mvp.contract.main.home.HomeContract
import com.myl.wanandroid.mvp.model.entity.AriticleResponse
import com.myl.wanandroid.mvp.model.main.home.HomeModel
import com.myl.wanandroid.mvp.ui.adapter.AriticleAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 13:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建HomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class HomeModule(private val view: HomeContract.View) {
    @FragmentScope
    @Provides
    fun provideHomeView(): HomeContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideHomeModel(model: HomeModel): HomeContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun getData():MutableList<AriticleResponse>{
        return mutableListOf()
    }

    @FragmentScope
    @Provides
    fun getAdapter(data:MutableList<AriticleResponse>):AriticleAdapter{
        return AriticleAdapter(data,true)
    }
}
