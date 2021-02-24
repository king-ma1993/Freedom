package com.myl.wanandroid.mvp.model.todo

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
import io.reactivex.Observable
import com.myl.wanandroid.mvp.contract.todo.AddTodoContract
import com.myl.wanandroid.mvp.model.api.Api
import com.myl.wanandroid.mvp.model.entity.ApiResponse
import javax.inject.Inject


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
class AddTodoModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddTodoContract.Model {


    @Inject
    lateinit var mGson: Gson
    @Inject
    lateinit var mApplication: Application


    override fun addTodo(title: String, content: String, date: String, type: Int, priority: Int): Observable<ApiResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(Api::class.java)
                .addTodo(title, content, date, type, priority)
    }

    override fun updateTodo(title: String, content: String, date: String, type: Int, priority: Int, id: Int): Observable<ApiResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(Api::class.java)
                .updateTodo(title, content, date, type, priority,id)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
