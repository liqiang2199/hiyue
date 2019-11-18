package com.hiyue_client.usercenter.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.extras.Utils
import com.hiyue.provider.common.RouterUtil
import com.hiyue.provider.ext.enAbleDefaultFalse
import com.hiyue.provider.ext.enAbleDefaultTrue
import com.hiyue.provider.ext.isNoNullOrEmpty
import com.hiyue_client.usercenter.R
import com.hiyue_client.usercenter.mvp.register.RegisterPersenter
import com.hiyue_client.usercenter.mvp.register.RegisterView
import com.lib.network.app.ActivityManager
import com.lib.network.ext.onClick
import com.lib.network.ui.activity.BaseMvpActivity
import com.lib.network.util.ToastUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

/**
 * 注册
 */
class RegisterActivity : BaseMvpActivity<RegisterView, RegisterPersenter>(), RegisterView {

    override fun initLayoutView(): Int {
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        return R.layout.activity_register
    }

    override fun initView() {
        ActivityManager.getInstance().addChildActivity(this)
        btnSend.onClick {
            val phone = editPhone.text.toString().trim()
            getPresenter().sendSms(this, editPhone.text.toString())
        }

        btRegister.onClick {
            //注册
            val phone = editPhone.text.toString().trim()
            val etPass = editPass.text.toString().trim()
            val etVerCode = editVerCode.text.toString().trim()

            getPresenter().userRegisterByMobileP(this, phone
                    , etPass, etVerCode)
        }

        ivBack.onClick {
            finish()
        }
    }

    override fun initIntentData(intent: Intent) {

    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {
        super.initViewInstanceState(savedInstanceState)
    }

    override fun createPresenter(): RegisterPersenter {
        return RegisterPersenter()
    }

    override fun sendSmsStateView(result: Boolean) {
        if (result) {
            //开始倒计时
            ToastUtil.Toast_ShortUtil(this, "验证码发送成功！")
            time()
        }
    }


    fun time() {
        Flowable.intervalRange(0, 121, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { aLong ->
                    //Log.d(TAG, "倒计时")
                    btnSend.setTextColor(Color.parseColor("#999999"))
                    btnSend.enAbleDefaultFalse()
                    btnSend.text = "" + (120 - aLong!!).toString() + " s"
                }
                .doOnComplete {
                    //Log.d(TAG, "倒计时完毕")
                    btnSend.setTextColor(Color.parseColor("#ffffff"))
                    btnSend.enAbleDefaultTrue()
                    btnSend.text = "重新发送"
                }
                .subscribe()
    }

    override fun registerSuccessLoginView(result: Boolean) {
        //跳转首页
        ActivityManager.getInstance().cleanChildStackActivity()
        //跳转首页
        ARouter.getInstance().build(RouterUtil.ROUTE_MAIN).navigation()
    }
}