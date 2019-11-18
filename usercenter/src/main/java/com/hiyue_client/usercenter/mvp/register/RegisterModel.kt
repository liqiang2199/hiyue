package com.hiyue_client.usercenter.mvp.register

import android.content.Context
import com.alibaba.fastjson.JSON
import com.example.extras.Utils
import com.hiyue.provider.common.ConstantUtil
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.ext.getParamsDb
import com.hiyue.provider.net.LoadingCallBack
import com.hiyue.provider.net.NetEncrypt
import com.hiyue.provider.net.NetCommon
import com.hiyue_client.usercenter.been.SendSmsBeen
import com.hiyue_client.usercenter.been.http.VerifyCodeHttpBeen
import com.hiyue_client.usercenter.been.json.RegisterBeen
import com.hiyue_client.usercenter.been.json.SendSmsStateBeen
import com.hiyue_client.usercenter.mvp.BaseUserModel
import com.lib.network.net.callBackRequest
import java.util.*

class RegisterModel : BaseUserModel<RegisterHandler>() {

    //{"code":1,"source":"database","msg":"短信发送频率太快！","time":"2019-07-03 21:23:42","ver":"1560218008","data":""}
    //{"data":{"state":true},"time":"2019-07-03 21:10:24","msg":"","code":0,"source":"database","ver":1560218008}
    fun sendSms(context: Context, mobile: String) {

        val sendSmsBeen = SendSmsBeen(receiver = mobile, call_index = "register")
        NetEncrypt.httpEncrypt("sms_send", sendSmsBeen)

        //val device = "android"
        val deviceIdentification = "00000000000000000000000000000000"
        val appVersion = "0.01"
        //val deviceVersion = "9.0"

        val deviceTime = Utils.getSystemDate()
        val deviceUnixTime = Utils.dateToStamp(deviceTime)
        val params = TreeMap<String, String>()
        params["m"] = "sms_send"
        params["d"] = sendSmsBeen.d
        params["t"] = deviceTime
        params["v"] = sendSmsBeen.v
        params["i"] = deviceIdentification
        params["e"] = "00000A"
        params["n"] = "0.01"
        params["b"] = "1"
        //params["c"] = NetEncrypt.getCodeCheck(deviceUnixTime, deviceIdentification, deviceVersion)
        params["c"] = NetEncrypt.getCodeCheck(deviceUnixTime, deviceIdentification, sendSmsBeen.v)
        //请求参数全在 params 上面是系统参数 下面是业务参数
        params["receiver"] = mobile
        params["call_index"] = "register"

        //val jsonData = JSON.toJSONString(params)


        val params1 = EncryptUtils.AES_Encode(Utils.convertTreeMapToJsonString(params)
                , ConstantUtil.ENCRP_KEY, ConstantUtil.ENCRP_IV)

        val sendSmsHttp = getApiService().commonRequest(
                sendSmsBeen.d, appVersion, params1)
        sendSmsHttp.callBackRequest(context, object : LoadingCallBack() {
            override fun callBackSuccess(data: String) {
                val stateBeen = JSON.parseObject(data, SendSmsStateBeen::class.java)
                getModelHandler().sendSmsStateH(stateBeen.state ?: false)
            }

            override fun callBackFailure(e: Throwable, msg: String, code: Int) {
                getModelHandler().sendSmsStateH(false)
            }


        })
    }


    /**
     * 通过手机号码注册用户
     */
    fun userRegisterByMobile(context: Context, mobile: String, pass: String, verCode: String) {

        val verifyCodeHttpBeen = VerifyCodeHttpBeen(mobile = mobile
                , verify_code = verCode, pass = EncryptUtils.md5(pass))


        NetEncrypt.httpEncrypt("user_register_by_mobile", verifyCodeHttpBeen)

        val jsonData = JSON.toJSONString(verifyCodeHttpBeen)

        NetCommon.httpCommon(jsonData, verifyCodeHttpBeen).callBackRequest(context
                , object : LoadingCallBack() {
            override fun callBackSuccess(data: String) {
                userDataBeen(data)
            }

            override fun callBackFailure(e: Throwable, msg: String, code: Int) {
            }


        })
    }
}