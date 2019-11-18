package com.hiyue_client.usercenter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import com.hiyue.provider.ext.enAbleDefaultFalse
import com.hiyue.provider.ext.enAbleDefaultTrue
import com.hiyue.provider.ext.isNoEmptyAndNull
import com.hiyue.provider.ext.reStr
import com.hiyue.provider.utils.TextWatcherUtil
import com.hiyue_client.usercenter.R
import com.hiyue_client.usercenter.mvp.forget.modify.ForgetModifyPresenter
import com.hiyue_client.usercenter.mvp.forget.modify.ForgetModifyView
import com.lib.network.app.ActivityManager
import com.lib.network.ext.onClick
import com.lib.network.ui.activity.BaseMvpActivity
import com.lib.network.util.ToastUtil
import kotlinx.android.synthetic.main.activity_reset_forget.*

import org.jetbrains.anko.find

class ForgetModifyPassActivity : BaseMvpActivity<ForgetModifyView, ForgetModifyPresenter>(),
        ForgetModifyView{

    private lateinit var but: Button

    private var phone: String = ""
    private var verCode: String = ""

    override fun initLayoutView(): Int {
        return R.layout.activity_reset_forget
    }

    override fun initView() {
        ActivityManager.getInstance().addChildActivity(this)
        but = find(R.id.but)
        but.text = reStr(R.string.sure_re_set_forget_pass)
        but.enAbleDefaultFalse()
        but.onClick {
            //跳转发送验证码界面
            val pass = editPass.getEditView().text.toString().trim()
            val passEdit = editRePass.getEditView().text.toString().trim()
            if (!pass.isNoEmptyAndNull()) {
                ToastUtil.Toast_ShortUtil(this, "请输入新密码")
                return@onClick
            }
            if (!passEdit.isNoEmptyAndNull()) {
                ToastUtil.Toast_ShortUtil(this, "请再次输入密码")
                return@onClick
            }
            if (!pass.equals(passEdit)) {
                ToastUtil.Toast_ShortUtil(this, "两次密码不一致，请重新输入")
                return@onClick
            }
            //提交 更新密码
            getPresenter().forgetModifyP(this, phone, pass, verCode)
        }
        editPass.setEditInputPass()
        editRePass.setEditInputPass()

        editPass.getEditView().addTextChangedListener(object : TextWatcherUtil() {
            override fun afterTextChanged(s: Editable?) {
                val str = s?.toString()?.trim()
                val passEdit = editRePass.getEditView().text.toString().trim()
                if (str.isNoEmptyAndNull() && passEdit.isNoEmptyAndNull()) {
                    but.enAbleDefaultTrue()
                }
            }
        })

        editRePass.getEditView().addTextChangedListener(object : TextWatcherUtil() {
            override fun afterTextChanged(s: Editable?) {
                val str = s?.toString()?.trim()
                val passEdit = editPass.getEditView().text.toString().trim()
                if (str.isNoEmptyAndNull() && passEdit.isNoEmptyAndNull()) {
                    but.enAbleDefaultTrue()
                }
            }
        })


    }

    override fun initIntentData(intent: Intent) {
        if (intent.hasExtra("phone")) {
            phone = intent.getStringExtra("phone")
            verCode = intent.getStringExtra("verCode")
        }
    }

    override fun createPresenter(): ForgetModifyPresenter {
        return ForgetModifyPresenter()
    }

    override fun findPassModifyResultView(result: Boolean) {
        //返回登录页面
        ActivityManager.getInstance().cleanChildStackActivity()
    }

}