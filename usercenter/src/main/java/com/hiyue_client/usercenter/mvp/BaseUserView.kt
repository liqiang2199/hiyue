package com.hiyue_client.usercenter.mvp

import com.lib.network.mvp.view.IBaseView

interface BaseUserView : IBaseView {

    /**
     * 发送短信验证码 回调
     */
    fun sendSmsStateView(result: Boolean) {}
}