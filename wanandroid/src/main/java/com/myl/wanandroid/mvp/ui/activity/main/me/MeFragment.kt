package com.myl.wanandroid.mvp.ui.activity.main.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.jess.arms.di.component.AppComponent
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.event.LoginFreshEvent
import com.myl.wanandroid.app.event.SettingChangeEvent
import com.myl.wanandroid.di.component.main.me.DaggerMeComponent
import com.myl.wanandroid.di.module.main.me.MeModule
import com.myl.wanandroid.mvp.contract.main.me.MeContract
import com.myl.wanandroid.mvp.model.entity.BannerResponse
import com.myl.wanandroid.mvp.model.entity.IntegralResponse
import com.myl.wanandroid.mvp.model.entity.UserInfoResponse
import com.myl.wanandroid.mvp.presenter.main.me.MePresenter
import com.myl.wanandroid.mvp.ui.BaseFragment
import com.myl.wanandroid.mvp.ui.activity.collect.CollectActivity
import com.myl.wanandroid.mvp.ui.activity.integral.IntegralActivity
import com.myl.wanandroid.mvp.ui.activity.setting.SettingActivity
import com.myl.wanandroid.mvp.ui.activity.share.ShareListActivity
import com.myl.wanandroid.mvp.ui.activity.start.LoginActivity
import com.myl.wanandroid.mvp.ui.activity.todo.TodoActivity
import com.myl.wanandroid.mvp.ui.activity.web.WebviewActivity
import org.greenrobot.eventbus.Subscribe
import android.net.Uri
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.myl.wanandroid.app.utils.CacheUtil
import com.myl.wanandroid.app.utils.setUiTheme
import com.myl.wanandroid.app.utils.startActivityKx
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import com.myl.wanandroid.app.utils.*


/**
 * 我的
 */
class MeFragment : BaseFragment<MePresenter>(), MeContract.View {

    private lateinit var userInfo: UserInfoResponse
    var integral: IntegralResponse? = null

    companion object {
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .meModule(MeModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        toolbar.run {
            title = ""
        }
        me_swipe.run {
            setOnRefreshListener {
                //刷新积分
                mPresenter?.getIntegral()
            }
        }
        me_swipe.setColorSchemeColors(SettingUtil.getColor(_mActivity))
        toolbar.setBackgroundColor(SettingUtil.getColor(_mActivity))
        me_linear.setBackgroundColor(SettingUtil.getColor(_mActivity))
        me_integral.setTextColor(SettingUtil.getColor(_mActivity))
        ArmsUtils.obtainAppComponentFromContext(_mActivity).imageLoader().loadImage(_mActivity.applicationContext,
                ImageConfigImpl
                        .builder()
                        .url("https://avatars2.githubusercontent.com/u/18655288?s=460&v=4")
                        .imageView(imageView)
                        .errorPic(R.drawable.ic_account)
                        .fallback(R.drawable.ic_account)
                        .placeholder(R.drawable.ic_account)
                        .isCrossFade(true)
                        .isCircle(true)
                        .build())
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (CacheUtil.isLogin()) {
            //如果登录了 赋值，并且请求积分接口
            userInfo = CacheUtil.getUser()
            me_name.text = if(userInfo.nickname.isEmpty()) userInfo.username else userInfo.nickname
            me_swipe.isRefreshing = true
            mPresenter?.getIntegral()
        } else {
            //没登录，就不要去请求积分接口了
            me_name.text = "请先登录~"
            me_info.text = "id : --　排名 : --"
            me_integral.text = "0"
        }
    }

    /**
     * 接收到登录或退出的EventBus 刷新数据
     */
    @Subscribe
    fun freshLogin(event: LoginFreshEvent) {
        if (event.login) {
            //接收到登录了，赋值 并去请求积分接口
            userInfo = CacheUtil.getUser()
            me_name.text = if(userInfo.nickname.isEmpty()) userInfo.username else userInfo.nickname
            //吊起请求 设置触发 下拉 swipe
            me_swipe.isRefreshing = true
            mPresenter?.getIntegral()
        } else {
            //接受到退出登录了，赶紧清空赋值
            me_name.text = "请先登录~"
            me_integral.text = "0"
            me_info.text = "id : --　排名 : --"
        }
    }

    /**
     * 接收到event时，重新设置当前界面控件的主题颜色和一些其他配置
     */
    @Subscribe
    fun settingEvent(event: SettingChangeEvent) {
        setUiTheme(_mActivity, listOf(me_swipe,toolbar,me_linear,me_integral))
    }

    @OnClick(R.id.me_setting, R.id.me_collect, R.id.me_linear, R.id.me_todo, R.id.me_integralLinear
            , R.id.me_article,R.id.me_join,R.id.me_about)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.me_linear -> {
                if (!CacheUtil.isLogin()) {
                    startActivityKx(LoginActivity::class.java)
                }
            }
            R.id.me_collect -> {
                startActivityKx(CollectActivity::class.java,true)
            }
            R.id.me_todo -> {
                startActivityKx(TodoActivity::class.java,true)
            }
            R.id.me_integralLinear -> {
                startActivityKx(IntegralActivity::class.java,true,Bundle().apply {
                    integral?.let {
                        putSerializable("integral", it)
                    }
                })
            }
            R.id.me_article -> {
                startActivityKx(ShareListActivity::class.java,true)
            }
            R.id.me_about ->{
                val data = BannerResponse("", 0, "", 0, 0, "玩Android网站", 0, "https://www.wanandroid.com/")
                startActivityKx(WebviewActivity::class.java,false,Bundle().apply {
                        putSerializable("bannerdata", data)
                })
            }
            R.id.me_join -> {
                joinQQGroup("9n4i5sHt4189d4DvbotKiCHy-5jZtD4D")
            }
            R.id.me_setting -> {
                startActivityKx(SettingActivity::class.java)
            }
        }
    }

    /**
     * 获取积分成功回调
     */
    override fun getIntegralSucc(integral: IntegralResponse) {
        this.integral = integral
        me_swipe.isRefreshing = false
        me_info.text = "id : ${integral.userId}　排名 : ${integral.rank}"
        me_integral.text = integral.coinCount.toString()
    }

    /**
     * 获取积分失败回调
     */
    override fun getIntegralFaild(errorMsg: String) {
        me_swipe.isRefreshing = false
        ShowUtils.showToast(_mActivity, errorMsg)
    }

    /**
     * 加入qq聊天群
     */
    fun joinQQGroup(key: String): Boolean {
        val intent = Intent()
        intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$key")
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return try {
            startActivity(intent)
            true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            ShowUtils.showToast(_mActivity,"未安装手机QQ或安装的版本不支持")
            false
        }
    }
}
