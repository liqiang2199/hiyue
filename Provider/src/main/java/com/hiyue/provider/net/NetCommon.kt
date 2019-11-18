package com.hiyue.provider.net

import com.alibaba.fastjson.JSON
import com.hiyue.provider.been.RequestBeen
import com.hiyue.provider.common.ConstantUtil
import com.hiyue.provider.encrypt.EncryptUtils
import com.lib.network.net.RetrofitManager
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 请求 公共内容
 */
object NetCommon {


    fun httpCommon(json: String, requestBeen: RequestBeen): Observable<ResponseBody> {
        val params1 = EncryptUtils.AES_Encode(json
                , ConstantUtil.ENCRP_KEY, ConstantUtil.ENCRP_IV)

        return RetrofitManager.getInstance().getApiService().commonRequest(
                requestBeen.d, requestBeen.v, params1)
    }

    /**
     * json 为任意类
     */
    fun httpCommon(json: Any, requestBeen: RequestBeen): Observable<ResponseBody> {

        val params1 = EncryptUtils.AES_Encode(JSON.toJSONString(json)
                , ConstantUtil.ENCRP_KEY, ConstantUtil.ENCRP_IV)

        return RetrofitManager.getInstance().getApiService().commonRequest(
                requestBeen.d, requestBeen.v, params1)
    }
}