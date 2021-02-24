package com.myl.wanandroid.mvp.contract.main.publicNumber

import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import com.myl.wanandroid.mvp.model.entity.ClassifyResponse


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 14:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
interface PublicContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun requestTitileSucc(titles:MutableList<ClassifyResponse>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getTitles(): Observable<ApiResponse<MutableList<ClassifyResponse>>>
    }

}
