package com.hiyue_client.usercenter.mvp.forget.modify

import android.content.Context
import com.alibaba.fastjson.JSON
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.net.LoadingCallBack
import com.hiyue.provider.net.NetCommon
import com.hiyue.provider.net.NetEncrypt
import com.hiyue.provider.net.UrlNetUtil
import com.hiyue_client.usercenter.been.http.LoginHttpBeen
import com.hiyue_client.usercenter.been.http.VerifyCodeHttpBeen
import com.hiyue_client.usercenter.mvp.BaseUserModel
import com.lib.network.net.callBackRequest

class ForgetModifyModel : BaseUserModel<ForgetModifyHandler>() {

    /**
     * 找回密码
     */
    fun forgetModify(context: Context, mobile: String, newPass: String, verCode: String) {

        val loginBeenHttp = VerifyCodeHttpBeen(mobile, EncryptUtils.md5(newPass), verCode)
        NetEncrypt.httpEncrypt(UrlNetUtil.USER_RESET_PASS, loginBeenHttp)

        val jsonData = JSON.toJSONString(loginBeenHttp)

        NetCommon.httpCommon(jsonData, loginBeenHttp).callBackRequest(context
                , object : LoadingCallBack() {
            override fun callBackSuccess(data: String) {
                getModelHandler().findPassModifyResultH(true)
            }

            override fun callBackFailure(e: Throwable, msg: String, code: Int) {

            }

        })

    }
}