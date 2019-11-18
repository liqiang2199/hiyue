package com.hiyue_client.usercenter.mvp.login

import com.hiyue_client.usercenter.mvp.BaseUserView

interface LoginView : BaseUserView {

    fun loginSuccessView(result: Boolean)
}