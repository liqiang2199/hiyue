package com.hiyue_client.usercenter.ui.activity

import android.content.Intent
import android.graphics.Color
import android.widget.Button
import com.example.extras.Utils
import com.hiyue.provider.ext.enAbleDefaultFalse
import com.hiyue.provider.ext.enAbleDefaultTrue
import com.hiyue_client.usercenter.R
import com.hiyue_client.usercenter.mvp.forget.ForgetPresenter
import com.hiyue_client.usercenter.mvp.forget.ForgetView
import com.jkb.vcedittext.VerificationAction
import com.lib.network.app.ActivityManager
import com.lib.network.ext.onClick
import com.lib.network.ui.activity.BaseMvpActivity
import com.lib.network.util.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_forget_ver_code.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit
import io.reactivex.Flowable
import android.widget.TextView


class ForgetVerActivity : BaseMvpActivity<ForgetView, ForgetPresenter>(), ForgetView {

    private lateinit var but: Button

    private var phone = ""
    private var verCode = ""


    override fun initLayoutView(): Int {
        return R.layout.activity_forget_ver_code
    }

    override fun initView() {
        ActivityManager.getInstance().addChildActivity(this)
        but = find(R.id.but)
        but.text = resources.getText(R.string.sure_edit_ver_code)
        but.enAbleDefaultFalse()
        but.onClick {
            //跳转发送验证码界面
            if (!Utils.isMobile(phone)) {
                ToastUtil.Toast_ShortUtil(this, "请输入正确的手机号")
                return@onClick
            }
            //
            startActivity<ForgetModifyPassActivity>("phone" to phone
                    , "verCode" to verCode)
        }
        //发送验证码
        getPresenter().forgetPassSendCode(this, phone)

        editVerCode.setOnVerificationCodeChangedListener(object : VerificationAction.OnVerificationCodeChangedListener{
            override fun onInputCompleted(s: CharSequence?) {
                println("${s.toString()}")
                if (s?.length == 6) {
                    verCode = s.toString().trim()
                    but.enAbleDefaultTrue()
                }

            }

            override fun onVerCodeChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("${s.toString()}")
                if (s?.length!! < 6) {
                    but.enAbleDefaultFalse()
                }
            }

        })
    }

    override fun initIntentData(intent: Intent) {
        if (intent.hasExtra("phone")) {
            phone = intent.getStringExtra("phone")
        }
    }

    override fun createPresenter(): ForgetPresenter {
        return ForgetPresenter()
    }

    override fun sendSmsStateView(result: Boolean) {
        //短信发送成功
        time(tvYzm)

    }

    fun time(tvCountDown: TextView) {
        Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { aLong ->
                    //Log.d(TAG, "倒计时")
                    tvCountDown.setTextColor(Color.parseColor("#999999"))
                    tvCountDown.isEnabled = false
                    tvCountDown.text = "验证码再次发送（" + (10 - aLong!!).toString() + " ）"
                }
                .doOnComplete {
                    //Log.d(TAG, "倒计时完毕")
                    tvCountDown.setTextColor(Color.parseColor("#159FEC"))
                    tvCountDown.isEnabled = true
                    tvCountDown.text = "验证码再次发送"
                }
                .subscribe()
    }

}