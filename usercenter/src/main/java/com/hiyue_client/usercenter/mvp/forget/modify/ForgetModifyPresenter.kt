package com.hiyue_client.usercenter.mvp.forget.modify

import android.content.Context
import com.hiyue_client.usercenter.mvp.BaseUserPersenter

class ForgetModifyPresenter : BaseUserPersenter<ForgetModifyView>() {
    private val forgetModifyHandler = object : ForgetModifyHandler {
        override fun findPassModifyResultH(result: Boolean) {
            getView()?.findPassModifyResultView(result)
        }
    }

    fun forgetModifyP(context: Context, mobile: String, newPass: String, verCode: String) {
        val forgetModifyModel = createModel(ForgetModifyModel())
        forgetModifyModel.setModelHandler(forgetModifyHandler)
        forgetModifyModel.forgetModify(context, mobile, newPass, verCode)
    }
}