package com.hiyue_client.usercenter.mvp.forget

import android.content.Context
import com.hiyue_client.usercenter.mvp.BaseUserModel

class ForgetModel : BaseUserModel<ForgetHandler>() {

    /**
     * 忘记密码
     */
    fun forgetPassSendCode(context: Context, mobile: String) {
        sendCodeBase(context, mobile, "resetpass") {
            //发送短信验证码
            getModelHandler().sendSmsStateH(true)
        }
    }
}