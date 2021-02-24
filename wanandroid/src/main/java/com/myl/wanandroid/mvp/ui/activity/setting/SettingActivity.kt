package com.myl.wanandroid.mvp.ui.activity.setting

import android.os.Bundle
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.event.SettingChangeEvent
import com.myl.wanandroid.mvp.ui.BaseActivity
import org.greenrobot.eventbus.Subscribe

class SettingActivity : BaseActivity<IPresenter>() {

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_setting
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar.run {
            setSupportActionBar(this)
            title = "设置"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.setting_frame, GeneralPreferenceFragment())
                .commit()
    }

    @Subscribe
    fun settingEvent(event: SettingChangeEvent) {
        initStatusBar()
    }
}
