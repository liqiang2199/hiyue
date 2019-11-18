package com.hiyue_client.usercenter.mvp.forget.modify

import com.hiyue_client.usercenter.mvp.BaseUserHandler

interface ForgetModifyHandler : BaseUserHandler {

    /**
     * 找回密码结果
     */
    fun findPassModifyResultH(result: Boolean)

}