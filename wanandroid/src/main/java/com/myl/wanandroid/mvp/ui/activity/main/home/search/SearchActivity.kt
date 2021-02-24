package com.myl.wanandroid.mvp.ui.activity.main.home.search

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.jess.arms.di.component.AppComponent
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.utils.CacheUtil
import com.myl.wanandroid.app.utils.ColorUtil
import com.myl.wanandroid.app.utils.SettingUtil
import com.myl.wanandroid.app.utils.ShowUtils
import com.myl.wanandroid.di.component.main.home.search.DaggerSearchComponent
import com.myl.wanandroid.di.module.main.home.search.SearchModule
import com.myl.wanandroid.mvp.contract.main.home.search.SearchContract
import com.myl.wanandroid.mvp.model.entity.SearchResponse
import com.myl.wanandroid.mvp.presenter.main.home.search.SearchPresenter
import com.myl.wanandroid.mvp.ui.BaseActivity
import com.myl.wanandroid.mvp.ui.adapter.SearchistoryAdapter
import kotlinx.android.synthetic.main.flow_layout.*


/**
 * 搜索
 * @Author:         hegaojian
 * @CreateDate:     2019/8/16 20:34
 */
class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    var mtagData = mutableListOf<SearchResponse>()//搜索热词数据

    var historyData = mutableListOf<String>()//搜索历史数据

    lateinit var adapter: SearchistoryAdapter//搜索历史适配器

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar.run {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                //返回的时候关闭当前界面输入法
                ShowUtils.hideSoftKeyboard(this@SearchActivity)
                finish()
            }
        }
        search_text1.setTextColor(SettingUtil.getColor(this))
        search_text2.setTextColor(SettingUtil.getColor(this))
        search_clear.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "温馨提示")
                message(text = "确定清空搜索历史吗？")
                positiveButton(text = "清空") {
                    historyData.clear()
                    adapter.setNewData(historyData)
                    CacheUtil.setSearchHistoryData(Gson().toJson(historyData))
                }
                negativeButton(R.string.cancel)
            }
        }
        search_flowlayout.run {
            setOnTagClickListener { view, position, parent ->
                val name = mtagData[position].name
                if (historyData.contains(name)) {
                    //当搜索历史中包含该数据时 删除添加
                    historyData.remove(name)
                } else if (historyData.size >= 10) {
                    historyData.removeAt(historyData.size - 1)
                }
                historyData.add(0, name)
                this@SearchActivity.adapter.setNewData(historyData)
                CacheUtil.setSearchHistoryData(Gson().toJson(historyData))
                launchActivity(Intent(this@SearchActivity, SearchResultActivity::class.java).apply {
                    putExtra("searchKey", name)
                })
                false
            }
        }
        historyData = CacheUtil.getSearchHistoryData()
        adapter = SearchistoryAdapter(historyData).apply {
            //设置空布局
            emptyView = LayoutInflater.from(this@SearchActivity).inflate(R.layout.search_empty_view, null)
            //删除单个搜索历史
            setOnItemChildClickListener { adapter, view, position ->
                adapter.remove(position)
                CacheUtil.setSearchHistoryData(Gson().toJson(historyData))
            }
            //点击了搜索历史的某一个
            setOnItemClickListener { adapter, view, position ->
                launchActivity(Intent(this@SearchActivity, SearchResultActivity::class.java).apply {
                    putExtra("searchKey", historyData[position])
                })
            }
        }
        search_recyclerview.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            adapter = this@SearchActivity.adapter
            isNestedScrollingEnabled = false
        }
        mPresenter?.getHotData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.run {
            maxWidth = Integer.MAX_VALUE
            onActionViewExpanded()
            queryHint = "输入关键字搜索"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                //searchview的监听
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //当点击搜索时 输入法的搜索，和右边的搜索都会触发
                    query?.let {
                        if (historyData.contains(it)) {
                            //当搜索历史中包含该数据时 删除
                            historyData.remove(it)
                        } else if (historyData.size >= 10) {
                            //如果集合的size 有10个以上了，删除最后一个
                            historyData.removeAt(historyData.size - 1)
                        }
                        launchActivity(Intent(this@SearchActivity, SearchResultActivity::class.java).apply {
                            putExtra("searchKey", it)
                        })
                        historyData.add(0, it)//添加新数据到第一条
                        this@SearchActivity.adapter.setNewData(historyData)//刷新适配器
                        CacheUtil.setSearchHistoryData(Gson().toJson(historyData))//保存到本地
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            isSubmitButtonEnabled = true //右边是否展示搜索图标
            val field = javaClass.getDeclaredField("mGoButton")
            field.run {
                isAccessible = true
                val mGoButton = get(searchView) as ImageView
                mGoButton.setImageResource(R.drawable.ic_search)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 获取搜索热词成功
     */
    override fun requestSearchSucc(tagData: MutableList<SearchResponse>) {
        mtagData.addAll(tagData)
        search_flowlayout.adapter = object : TagAdapter<SearchResponse>(mtagData) {
            override fun getView(parent: FlowLayout?, position: Int, hotSearchBean: SearchResponse?): View {
                return LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout, search_flowlayout, false)
                        .apply {
                            flow_tag.text = Html.fromHtml(hotSearchBean?.name)
                            flow_tag.setTextColor(ColorUtil.randomColor())
                        }
            }
        }
    }
}
