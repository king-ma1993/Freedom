package com.myl.wanandroid.mvp.ui.activity.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.jess.arms.di.component.AppComponent
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.event.CollectEvent
import com.myl.wanandroid.app.event.LoginFreshEvent
import com.myl.wanandroid.app.utils.CacheUtil
import com.myl.wanandroid.app.utils.startActivityKx
import com.myl.wanandroid.app.weight.LollipopFixedWebView
import com.myl.wanandroid.di.component.web.DaggerWebviewComponent
import com.myl.wanandroid.di.module.web.WebviewModule
import com.myl.wanandroid.mvp.contract.web.WebviewContract
import com.myl.wanandroid.mvp.model.entity.AriticleResponse
import com.myl.wanandroid.mvp.model.entity.BannerResponse
import com.myl.wanandroid.mvp.model.entity.CollectResponse
import com.myl.wanandroid.mvp.model.entity.CollectUrlResponse
import com.myl.wanandroid.mvp.model.entity.enums.CollectType
import com.myl.wanandroid.mvp.presenter.web.WebviewPresenter
import com.myl.wanandroid.mvp.ui.BaseActivity
import com.myl.wanandroid.mvp.ui.activity.start.LoginActivity
import org.greenrobot.eventbus.Subscribe

class WebviewActivity : BaseActivity<WebviewPresenter>(), WebviewContract.View {

    //是否收藏
    var collect = false
    //id
    var id = 0
    lateinit var showTitle: String
    //标题
    lateinit var url: String
    //需要收藏的类型 具体参数说明请看 CollectType 枚举类
    var collectTYpe = 0

    private lateinit var mAgentWeb: AgentWeb

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerWebviewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .webviewModule(WebviewModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_webview //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        //因为有多个地方进入详情且数据结构不同，如文章，轮播页，收藏文章列表，收藏网址列表等，做收藏的话需要判断，这里搞了感觉很多余的处理
        //点击文章进来的
        intent.getSerializableExtra("data")?.let {
            it as AriticleResponse
            id = it.id
            showTitle = it.title
            collect = it.collect
            url = it.link
            collectTYpe = CollectType.Ariticle.type
        }
        //点击首页轮播图进来的
        intent.getSerializableExtra("bannerdata")?.let {
            it as BannerResponse
            id = it.id
            showTitle = it.title
            collect = false //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
            url = it.url
            collectTYpe = CollectType.Url.type
        }
        //从收藏文章列表点进来的
        intent.getSerializableExtra("collect")?.let {
            it as CollectResponse
            id = it.originId
            showTitle = it.title
            collect = true //从收藏列表过来的，肯定 是 true 了
            url = it.link
            collectTYpe = CollectType.Ariticle.type
        }
        //点击收藏网址列表进来的
        intent.getSerializableExtra("collectUrl")?.let {
            it as CollectUrlResponse
            id = it.id
            showTitle = it.name
            collect = true//从收藏列表过来的，肯定 是 true 了
            url = it.link
            collectTYpe = CollectType.Url.type
        }

        toolbar.run {
            setSupportActionBar(this)
            title = Html.fromHtml(showTitle)
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }

        //加载网页
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webview_content, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebView(LollipopFixedWebView(this))
                .createAgentWeb()
                .ready()
                .go(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        //如果收藏了，右上角的图标相对应改变
        if (collect) {
            menu.findItem(R.id.web_collect).icon = ContextCompat.getDrawable(this, R.drawable.ic_collected)
        } else {
            menu.findItem(R.id.web_collect).icon = ContextCompat.getDrawable(this, R.drawable.ic_collect)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "$showTitle:$url")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb.urlLoader.reload()
            }
            R.id.web_collect -> {
                //点击收藏
                //是否已经登录了，没登录需要跳转到登录页去
                if (CacheUtil.isLogin()) {
                    //是否已经收藏了
                    if (collect) {
                        if (collectTYpe == CollectType.Url.type) {
                            //取消收藏网址
                            mPresenter?.uncollectUrl(id)
                        } else {
                            //取消收藏文章
                            mPresenter?.uncollect(id)
                        }
                    } else {
                        if (collectTYpe == CollectType.Url.type) {
                            //收藏网址
                            mPresenter?.collectUrl(showTitle, url)
                        } else {
                            //收藏文章
                            mPresenter?.collect(id)
                        }

                    }
                } else {
                    //跳转到登录页
                    startActivityKx(LoginActivity::class.java)
                }
            }
            R.id.web_liulanqi -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 收藏回调 不管成功或失败都会进来
     */
    override fun collect(collected: Boolean) {
        collect = collected
        //刷新一下menu
        window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
        invalidateOptionsMenu()
        //通知app刷新相对应ID的数据的收藏的值
        CollectEvent(collected, id).post()
    }

    /**
     * 收藏网址成功回调
     */
    override fun collectUrlSucc(collected: Boolean, data: CollectUrlResponse) {
        collect = collected
        //刷新一下menu
        window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
        invalidateOptionsMenu()
        id = data.id
        //通知app刷新相对应ID的数据的收藏的值
        CollectEvent(collected, id).post()
    }

    @Subscribe
    fun freshLogin(event: LoginFreshEvent) {
        //如果是登录了， 当前界面的id与账户收藏集合id匹配的值需要设置已经收藏 并刷新menu
        if (event.login) {
            event.collectIds.forEach {
                if (it.toInt() == id) {
                    collect = true
                    window.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                    invalidateOptionsMenu()
                    return@forEach
                }
            }
        }
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}
