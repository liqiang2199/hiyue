package com.hiyue_client.usercenter.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.example.extras.Utils
import com.hiyue.provider.common.RouterUtil
import com.hiyue.provider.ext.isNoNullOrEmpty
import com.hiyue_client.usercenter.ChoiceLoginActivity
import com.hiyue_client.usercenter.R
import com.hiyue_client.usercenter.mvp.login.LoginPersenter
import com.hiyue_client.usercenter.mvp.login.LoginView
import com.hiyue_client.usercenter.ui.activity.ForgetActivity
import com.lib.network.app.ActivityManager
import com.lib.network.ext.onClick
import com.lib.network.ui.BaseActivity
import com.lib.network.ui.activity.BaseMvpActivity
import com.lib.network.util.ToastUtil
import kotlinx.android.synthetic.main.activity_choice_login.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * 登录
 */
class LoginActivity : BaseMvpActivity<LoginView, LoginPersenter>(), LoginView {

    override fun initLayoutView(): Int {
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        return R.layout.activity_login
    }

    override fun initView() {
        ActivityManager.getInstance().addChildActivity(this)
        btLogin.onClick {
            ARouter.getInstance().build(RouterUtil.ROUTE_MAIN).navigation()
//            val phone = editPhone.text.toString().trim()
//            if (!Utils.isMobile(phone)) {
//                ToastUtil.Toast_ShortUtil(this, "请输入正确的手机号")
//                return@onClick
//            }
//            val pass = editPassWord.text.toString().trim()
//            if (!pass.isNoNullOrEmpty()) {
//                ToastUtil.Toast_ShortUtil(this, "请输入密码")
//                return@onClick
//            }
//
//            if (pass.length < 6) {
//                ToastUtil.Toast_ShortUtil(this, "密码位数不正确，请重新输入")
//                return@onClick
//            }
//
//            getPresenter().login(this, phone, pass)
        }

        tvForget.onClick {
            //忘记密码
            startActivity<ForgetActivity>()
        }
        ivBack.onClick {
            finish()
        }

    }

    override fun initIntentData(intent: Intent) {

    }

    override fun createPresenter(): LoginPersenter {
        return LoginPersenter()
    }

    override fun loginSuccessView(result: Boolean) {
        ActivityManager.getInstance().cleanChildStackActivity()
        //跳转首页
        ARouter.getInstance().build(RouterUtil.ROUTE_MAIN).navigation()
    }

}