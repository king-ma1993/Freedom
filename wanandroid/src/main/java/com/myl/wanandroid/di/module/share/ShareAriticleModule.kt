package com.myl.wanandroid.di.module.share

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.myl.wanandroid.mvp.contract.share.ShareAriticleContract
import com.myl.wanandroid.mvp.model.share.ShareAriticleModel


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
@Module
//构建ShareAriticleModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ShareAriticleModule(private val view: ShareAriticleContract.View) {
    @ActivityScope
    @Provides
    fun provideShareAriticleView(): ShareAriticleContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideShareAriticleModel(model: ShareAriticleModel): ShareAriticleContract.Model {
        return model
    }
}
