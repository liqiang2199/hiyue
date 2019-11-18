package com.hiyue_client.usercenter.mvp.login

import android.content.Context
import com.hiyue_client.usercenter.mvp.BaseUserPersenter

class LoginPersenter : BaseUserPersenter<LoginView>() {
    private val loginHandler = object : LoginHandler {
        override fun registerSuccessLoginH(result: Boolean) {
            getView()?.loginSuccessView(result)
        }
    }

    /**
     * 登录
     */
    fun login(context: Context, mobile: String, pass: String) {
        val loginModel = createModel(LoginModel())
        loginModel.setModelHandler(loginHandler)
        loginModel.login(context, mobile, pass)
    }
}