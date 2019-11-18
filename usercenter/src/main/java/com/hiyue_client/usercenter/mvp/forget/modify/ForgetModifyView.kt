package com.hiyue_client.usercenter.mvp.forget.modify

import com.hiyue_client.usercenter.mvp.BaseUserView

interface ForgetModifyView : BaseUserView {

    /**
     * 找回密码结果
     */
    fun findPassModifyResultView(result: Boolean)
}