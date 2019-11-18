package com.hiyue_client.usercenter.mvp

import com.lib.network.mvp.handler.BaseHandler

interface BaseUserHandler : BaseHandler {

    /**
     * 发送短信验证码 回调
     */
    fun sendSmsStateH(result: Boolean) {}

    /**
     * 注册成功后跳转登录页面
     */
    fun registerSuccessLoginH(result: Boolean) {}
}