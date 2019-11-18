package com.hiyue_client.usercenter.mvp.register

import android.content.Context
import com.example.extras.Utils
import com.hiyue.provider.ext.isNoNullOrEmpty
import com.hiyue_client.usercenter.mvp.BaseUserPersenter
import com.lib.network.util.ToastUtil

class RegisterPersenter : BaseUserPersenter<RegisterView>() {

    private val registerHandler = object : RegisterHandler {
        override fun registerSuccessLoginH(result: Boolean) {
            getView()?.registerSuccessLoginView(result)
        }

        override fun sendSmsStateH(result: Boolean) {
            getView()?.sendSmsStateView(result)
        }
    }

    fun sendSms(context: Context, mobile: String) {
        if (!Utils.isMobile(mobile)) {
            ToastUtil.Toast_ShortUtil(context, "请输入正确的手机号")
            return
        }
        val registerModel = createModel(RegisterModel())
        registerModel.setModelHandler(registerHandler)
        registerModel.sendSms(context, mobile)
    }

    /**
     * 通过手机号码注册用户
     */
    fun userRegisterByMobileP(context: Context, mobile: String, pass: String, verCode: String) {
        if (!Utils.isMobile(mobile)) {
            ToastUtil.Toast_ShortUtil(context, "请输入正确的手机号")
            return
        }

        if (!pass.isNoNullOrEmpty()) {
            ToastUtil.Toast_ShortUtil(context, "请输入密码")
            return
        }

        if (!verCode.isNoNullOrEmpty()) {
            ToastUtil.Toast_ShortUtil(context, "请输入验证码")
            return
        }
        val registerModel = createModel(RegisterModel())
        registerModel.setModelHandler(registerHandler)
        registerModel.userRegisterByMobile(context, mobile, pass, verCode)
    }
}