package com.hiyue_client.usercenter.mvp.register

import android.content.Context
import com.hiyue_client.usercenter.mvp.BaseUserPersenter

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
        val registerModel = createModel(RegisterModel())
        registerModel.setModelHandler(registerHandler)
        registerModel.sendSms(context, mobile)
    }

    /**
     * 通过手机号码注册用户
     */
    fun userRegisterByMobileP(context: Context, mobile: String, pass: String, verCode: String) {
        val registerModel = createModel(RegisterModel())
        registerModel.setModelHandler(registerHandler)
        registerModel.userRegisterByMobile(context, mobile, pass, verCode)
    }
}