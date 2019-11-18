package com.hiyue.provider.net

import android.util.Log
import com.alibaba.fastjson.JSON
import com.hiyue.provider.been.ResponseBeen
import com.hiyue.provider.common.ConstantUtil
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.ext.isNoNullOrEmpty
import com.lib.network.net.BaseObserver
import com.lib.network.util.ToastUtil
import okhttp3.ResponseBody

abstract class LoadingCallBack : BaseObserver() {

    override fun onSuccess(body: ResponseBody) {
        val json = body.string()
        Log.e("callBacksuccess", " 返回参数 " +
                "${EncryptUtils.AES_Decode(json, ConstantUtil.ENCRP_KEY
                        , ConstantUtil.ENCRP_IV)} ")
        if (json.isNoNullOrEmpty()) {
            val jsonBody = EncryptUtils.AES_Decode(json, ConstantUtil.ENCRP_KEY
                    , ConstantUtil.ENCRP_IV)
            val dateBeen = JSON.parseObject(jsonBody, ResponseBeen::class.java)
            //请求成功
            if (dateBeen.code != null && dateBeen.code == 0) {
                if (dateBeen.data.isNoNullOrEmpty()) {
                    callBackSuccess(dateBeen.data!!)
                } else {
                    msg(dateBeen)
                }

            } else {
                msg(dateBeen)
            }
        }

    }

    /**
     * msg 处理
     */
    private fun msg(dateBeen: ResponseBeen) {
        callBackFailure(KotlinNullPointerException(""), dateBeen.msg ?: ""
                , dateBeen.code ?: -1001)

        getMContext()?.let {
            ToastUtil.Toast_ShortUtil(it.applicationContext, dateBeen.msg ?: "")
        }

    }

    override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
        if (isNetWorkError) {
            //网络错误
            callBackFailure(e, "网络错误，请稍后重试！", -1000)
        } else {
            callBackFailure(e, "网络错误，请稍后重试！", -1000)
        }
    }

    override fun onCodeError(e: Throwable, code: Int, msg: String) {

    }

    /**
     * 请求成功
     */
    abstract fun callBackSuccess(data: String)

    /**
     * 失败
     */
    abstract fun callBackFailure(e: Throwable, msg: String, code: Int)

}