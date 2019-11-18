package com.hiyue_client.usercenter.mvp.register

import com.hiyue_client.usercenter.mvp.BaseUserView


interface RegisterView : BaseUserView {

    /**
     * 注册成功后跳转登录页面
     */
    fun registerSuccessLoginView(result: Boolean)

}