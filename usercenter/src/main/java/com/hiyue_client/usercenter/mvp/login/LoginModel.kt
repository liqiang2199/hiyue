package com.hiyue_client.usercenter.mvp.login

import android.content.Context
import com.alibaba.fastjson.JSON
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.net.LoadingCallBack
import com.hiyue.provider.net.NetCommon
import com.hiyue.provider.net.NetEncrypt
import com.hiyue_client.usercenter.been.http.LoginHttpBeen
import com.hiyue_client.usercenter.mvp.BaseUserModel
import com.lib.network.net.callBackRequest

class LoginModel : BaseUserModel<LoginHandler>() {

    /**
     * 登录
     */
    fun login(context: Context, mobile: String, pass: String) {
        val loginBeenHttp = LoginHttpBeen(mobile, EncryptUtils.md5(pass))
        NetEncrypt.httpEncrypt("user_login", loginBeenHttp)

        val jsonData = JSON.toJSONString(loginBeenHttp)

        NetCommon.httpCommon(jsonData, loginBeenHttp).callBackRequest(context
                , object : LoadingCallBack() {
            override fun callBackSuccess(data: String) {
                userDataBeen(data)
            }

            override fun callBackFailure(e: Throwable, msg: String, code: Int) {

            }

        })

    }

}