package com.hiyue_client.usercenter.mvp

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.hiyue.provider.common.ConstantUtil
import com.hiyue.provider.ext.getParamsDb
import com.hiyue.provider.net.LoadingCallBack
import com.hiyue.provider.net.NetCommon
import com.hiyue.provider.net.NetEncrypt
import com.hiyue_client.usercenter.been.SendSmsBeen
import com.hiyue_client.usercenter.been.json.RegisterBeen
import com.hiyue_client.usercenter.net.UserService
import com.lib.network.mvp.model.BaseModel
import com.lib.network.net.ApiService
import com.lib.network.net.RetrofitManager
import com.lib.network.net.callBackRequest

abstract class BaseUserModel<H : BaseUserHandler> : BaseModel<H>() {

    /**
     * 服务
     */
    fun getUserService(): UserService {
        return RetrofitManager.getInstance().setCreate(UserService::class.java)
    }

    fun getApiService(): ApiService {
        return RetrofitManager.getInstance().getApiService()
    }

    /**
     * 登录用户数据处理
     */
    fun userDataBeen(data: String) {
        val registerBeen = JSON.parseObject(data, RegisterBeen::class.java)
        //注册成功后直接登录
        if (registerBeen.state == true) {
            getParamsDb().setValue(ConstantUtil.USER_TOKEN, registerBeen.usertoken ?: "")
            getParamsDb().setValue(ConstantUtil.USER_MOBILE,
                    registerBeen.userdata?.mobile ?: "")
            getParamsDb().setValue(ConstantUtil.USER_NAME
                    , registerBeen.userdata?.name ?: "")
            getParamsDb().setValue(ConstantUtil.USER_HEAD_IMAGE
                    , registerBeen.userdata?.headimg ?: "")
            getModelHandler().registerSuccessLoginH(true)
        }
    }

    /**
     * 发送短信验证码
     */
    fun sendCodeBase(context: Context, mobile: String, category: String
                     , send: (data: String) -> Unit) {
        val sendSmsBeen = SendSmsBeen(receiver = mobile, call_index = category)
        NetEncrypt.httpEncrypt("sms_send", sendSmsBeen)

        val jsonData = JSON.toJSONString(sendSmsBeen)

        NetCommon.httpCommon(jsonData, sendSmsBeen).callBackRequest(context
                , object : LoadingCallBack() {
            override fun callBackSuccess(data: String) {
                send(data)
            }

            override fun callBackFailure(e: Throwable, msg: String, code: Int) {
                Log.e("sendCodeBase" , "e = $e  msg = $msg code = $code")
            }

        })
    }
}