package com.myl.wanandroid.mvp.ui.activity.start

import android.os.Bundle
import android.text.InputType
import android.view.View
import butterknife.OnClick
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.include_toolbar.*
import com.myl.wanandroid.R
import com.myl.wanandroid.app.event.LoginFreshEvent
import com.myl.wanandroid.app.utils.CacheUtil
import com.myl.wanandroid.app.utils.SettingUtil
import com.myl.wanandroid.app.utils.afterTextChange
import com.myl.wanandroid.di.component.start.DaggerLoginComponent
import com.myl.wanandroid.di.module.start.LoginModule
import com.myl.wanandroid.mvp.contract.start.LoginContract
import com.myl.wanandroid.mvp.model.entity.UserInfoResponse
import com.myl.wanandroid.mvp.presenter.start.LoginPresenter
import com.myl.wanandroid.mvp.ui.BaseActivity


/**
 * 注册
 */
class RegisterActivity : BaseActivity<LoginPresenter>(), LoginContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(LoginModule(this))
                .build()
                .inject1(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_register //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        toolbar.run {
            setSupportActionBar(this)
            title = "注册"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
        }
        SettingUtil.setShapColor(register_sub, SettingUtil.getColor(this))
        login_goregister?.setTextColor(SettingUtil.getColor(this))

        register_username.afterTextChange {
            if (it.isNotEmpty()) {
                register_clear.visibility = View.VISIBLE
            } else {
                register_clear.visibility = View.GONE
            }
        }
        register_pwd.afterTextChange {
            if (it.isNotEmpty()) {
                register_key.visibility = View.VISIBLE
            } else {
                register_key.visibility = View.GONE
            }
        }
        register_pwd1.afterTextChange {
            if (it.isNotEmpty()) {
                register_key1.visibility = View.VISIBLE
            } else {
                register_key1.visibility = View.GONE
            }
        }
        register_key.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                register_pwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                register_pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            register_pwd.setSelection(register_pwd.text.toString().length)
        }
        register_key1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                register_pwd1.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                register_pwd1.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            register_pwd1.setSelection(register_pwd1.text.toString().length)
        }
    }


    @OnClick(R.id.register_clear, R.id.register_sub)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.register_clear -> register_username.setText("")
            R.id.register_sub -> {
                when {
                    register_username.text.isEmpty() -> showMessage("请填写账号")
                    register_username.text.length < 6 -> showMessage("账号长度不能小于6位")
                    register_pwd.text.isEmpty() -> showMessage("请填写密码")
                    register_pwd.text.length < 6 -> showMessage("密码长度不能小于6位")
                    register_pwd1.text.isEmpty() -> showMessage("请填写确认密码")
                    register_pwd1.text.toString() != register_pwd.text.toString() -> showMessage("密码不一致")
                    else -> mPresenter?.register(register_username.text.toString(), register_pwd.text.toString(), register_pwd1.text.toString())
                }
            }
        }
    }

    override fun onSucc(userinfo: UserInfoResponse) {
        CacheUtil.setUser(userinfo)//保存账户信息
        AppManager.getAppManager().killActivity(LoginActivity::class.java)
        LoginFreshEvent(true, userinfo.collectIds).post()
        finish()
    }

}
