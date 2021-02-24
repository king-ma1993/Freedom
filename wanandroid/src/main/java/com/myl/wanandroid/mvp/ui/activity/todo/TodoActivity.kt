package com.myl.wanandroid.mvp.ui.activity.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.jess.arms.di.component.AppComponent
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.activity_integral.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.event.AddEvent
import com.myl.wanandroid.app.utils.RecyclerViewUtils
import com.myl.wanandroid.app.utils.SettingUtil
import com.myl.wanandroid.app.weight.DefineLoadMoreView
import com.myl.wanandroid.app.weight.loadCallBack.EmptyCallback
import com.myl.wanandroid.app.weight.loadCallBack.ErrorCallback
import com.myl.wanandroid.app.weight.loadCallBack.LoadingCallback
import com.myl.wanandroid.di.component.todo.DaggerTodoComponent
import com.myl.wanandroid.di.module.todo.TodoModule
import com.myl.wanandroid.mvp.contract.todo.TodoContract
import com.myl.wanandroid.mvp.model.entity.ApiPagerResponse
import com.myl.wanandroid.mvp.model.entity.TodoResponse
import com.myl.wanandroid.mvp.presenter.todo.TodoPresenter
import com.myl.wanandroid.mvp.ui.BaseActivity
import com.myl.wanandroid.mvp.ui.adapter.TodoAdapter
import org.greenrobot.eventbus.Subscribe

/**
 * TODO任务清单
 * @Author:         hegaojian
 * @CreateDate:     2019/9/1 13:42
 */
class TodoActivity : BaseActivity<TodoPresenter>(), TodoContract.View {

    lateinit var loadsir: LoadService<Any>
    lateinit var adapter: TodoAdapter
    private var initPageNo = 1
    private var pageNo: Int = initPageNo //当前页码
    private var footView: DefineLoadMoreView? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerTodoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .todoModule(TodoModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_todo //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar.run {
            setSupportActionBar(this)
            title = "待办清单"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
        //绑定loadsir
        loadsir = LoadSir.getDefault().register(swipeRefreshLayout) {
            //界面加载失败，或者没有数据时，点击重试的监听
            loadsir.showCallback(LoadingCallback::class.java)
            pageNo = initPageNo
            mPresenter?.getTodoData(pageNo)
        }.apply {
            SettingUtil.setLoadingColor(this@TodoActivity, this)
            showCallback(LoadingCallback::class.java)
        }
        //初始化adapter
        adapter = TodoAdapter(arrayListOf()).apply {
            if (SettingUtil.getListMode(this@TodoActivity) != 0) {
                openLoadAnimation(SettingUtil.getListMode(this@TodoActivity))
            } else {
                closeLoadAnimation()
            }
            setOnItemClickListener { adapter, view, position ->
                launchActivity(Intent(this@TodoActivity, AddTodoActivity::class.java).apply {
                    putExtras(Bundle().apply {
                        putSerializable("data", this@TodoActivity.adapter.data[position])
                    })
                })
            }
            setOnItemChildClickListener { adapter1, view, position ->
                MaterialDialog(this@TodoActivity).show {
                    listItems(items = adapter.data[position].isDone().let {
                        if (it) {
                            listOf("编辑", "删除")
                        } else {
                            listOf("编辑", "删除", "完成")
                        }
                    }) { dialog, index, text ->
                        if (index == 0) {
                            launchActivity(Intent(this@TodoActivity, AddTodoActivity::class.java).apply {
                                putExtras(Bundle().apply {
                                    putSerializable("data", this@TodoActivity.adapter.data[position])
                                })
                            })
                        } else if (index == 1) {
                            mPresenter?.delTodo(adapter.data[position].id, position)
                        } else if (index == 2) {
                            mPresenter?.updateTodo(adapter.data[position].id, position)
                        }
                    }
                }
            }
        }
        floatbtn.run {
            backgroundTintList = SettingUtil.getOneColorStateList(this@TodoActivity)
            setOnClickListener {
                val layoutManager = swiperecyclerview.layoutManager as LinearLayoutManager
                //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
                if (layoutManager.findLastVisibleItemPosition() >= 40) {
                    swiperecyclerview.scrollToPosition(0)//没有动画迅速返回到顶部(马上)
                } else {
                    swiperecyclerview.smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
                }
            }
        }
        //初始化 swipeRefreshLayout
        swipeRefreshLayout.run {
            setColorSchemeColors(SettingUtil.getColor(this@TodoActivity))
            setOnRefreshListener {
                //刷新
                pageNo = initPageNo
                mPresenter?.getTodoData(pageNo)
            }
        }
        //初始化recyclerview
        footView = RecyclerViewUtils().initRecyclerView(this, swiperecyclerview, SwipeRecyclerView.LoadMoreListener {
            //加载更多
            mPresenter?.getTodoData(pageNo)
        }).apply {
            setLoadViewColor(SettingUtil.getOneColorStateList(this@TodoActivity))
        }

        //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
        swiperecyclerview.run {
            adapter = this@TodoActivity.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @SuppressLint("RestrictedApi")
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(-1)) {
                        floatbtn.visibility = View.INVISIBLE
                    }
                }
            })
        }
        //发起请求
        mPresenter?.getTodoData(pageNo)
    }

    @Subscribe
    fun todoChange(event: AddEvent) {
        //刷新
        if(event.code== AddEvent.TODO_CODE){
            swipeRefreshLayout.isRefreshing = true
            pageNo = initPageNo
            mPresenter?.getTodoData(pageNo)
        }
    }

    override fun requestDataSucces(ariticles: ApiPagerResponse<MutableList<TodoResponse>>) {
        swipeRefreshLayout.isRefreshing = false
        if (pageNo == initPageNo && ariticles.datas.size == 0) {
            //如果是第一页，并且没有数据，页面提示空布局
            loadsir.showCallback(EmptyCallback::class.java)
        } else if (pageNo == initPageNo) {
            loadsir.showSuccess()
            //如果是刷新的话，floatbutton就要隐藏了，因为这时候肯定是要在顶部的
            floatbtn.visibility = View.INVISIBLE
            adapter.setNewData(ariticles.datas)
        } else {
            //不是第一页
            loadsir.showSuccess()
            adapter.addData(ariticles.datas)
        }
        pageNo++
        if (ariticles.pageCount >= pageNo) {
            //如果总条数大于当前页数时 还有更多数据
            swiperecyclerview.loadMoreFinish(false, true)
        } else {
            //没有更多数据
            swiperecyclerview.postDelayed({
                //解释一下为什么这里要延时0.2秒操作。。。
                //因为上面的adapter.addData(data) 数据刷新了适配器，是需要时间的，还没刷新完，这里就已经执行了没有更多数据
                //所以在界面上会出现一个小bug，刷新最后一页的时候，没有更多数据啦提示先展示出来了，然后才会加载出请求到的数据
                //暂时还没有找到好的方法，就用这个处理一下，如果觉得没什么影响的可以去掉这个延时操作，或者有更好的解决方式可以告诉我一下
                swiperecyclerview.loadMoreFinish(false, false)
            }, 200)
        }
    }

    override fun requestDataFaild(errorMsg: String) {
        swipeRefreshLayout.isRefreshing = false
        if (pageNo == initPageNo) {
            //如果页码是 初始页 说明是刷新，界面切换成错误页
            loadsir.setCallBack(ErrorCallback::class.java) { _, view ->
                //设置错误页文字错误提示
                view.findViewById<TextView>(R.id.error_text).text = errorMsg
            }
            //设置错误
            loadsir.showCallback(ErrorCallback::class.java)
        } else {
            //页码不是0 说明是加载更多时出现的错误，设置recyclerview加载错误，
            swiperecyclerview.loadMoreError(0, errorMsg)
        }
    }

    override fun updateTodoDataSucc(position: Int) {
        //完成 待办清单 成功
        adapter.data[position].status = 1
        adapter.notifyItemChanged(position)
    }

    override fun deleteTodoDataSucc(position: Int) {
        //删除 待办清单 成功
        adapter.remove(position)
        if (adapter.data.size == 0) {
            pageNo = initPageNo
            mPresenter?.getTodoData(pageNo)
        }
    }

    override fun updateTodoDataFaild(errorMsg: String) {
        //删除或完成 待办清单 失败
        showMessage(errorMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.todo_add -> {
                launchActivity(Intent(this, AddTodoActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
