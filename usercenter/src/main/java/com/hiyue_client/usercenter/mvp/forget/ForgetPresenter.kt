package com.hiyue_client.usercenter.mvp.forget

import android.content.Context
import com.hiyue_client.usercenter.mvp.BaseUserPersenter

class ForgetPresenter : BaseUserPersenter<ForgetView>() {

    private val forgetHandler = object : ForgetHandler {
        override fun sendSmsStateH(result: Boolean) {
            getView()?.sendSmsStateView(result)
        }
    }

    /**
     * 忘记密码
     */
    fun forgetPassSendCode(context: Context, mobile: String) {

        val forgetModel = createModel(ForgetModel())
        forgetModel.setModelHandler(forgetHandler)
        forgetModel.forgetPassSendCode(context, mobile)
    }
}