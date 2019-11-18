package com.lib.network.net

import android.content.Context
import android.util.Log
import com.lib.network.been.DateJsonBeen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.ResponseBody

object NetWorkApi {
    //string: (re: ResponseBody) -> Unit
    //fun testNet(context: Context,req: DateJsonBeen, string: (re: ResponseBody) -> Unit) {
    fun testNet(context: Context,device: String,version: String,params: String
                , string: (re: ResponseBody) -> Unit) {
        val obable = RetrofitManager.getInstance()
                .getApiService()
                .commonRequest(device, version, params)

        obable.callBackRequest(context, object : BaseObserver() {
            override fun onCodeError(e: Throwable, code: Int, msg: String) {

            }

            override fun onSuccess(body: ResponseBody) {

                string(body)
            }

            override fun onFailure(e: Throwable, isNetWorkError: Boolean) {
                Log.e("onFailure", " 返回参数 $e ")

            }

        })

//        RetrofitManager.getInstance()
//                .getApiService().getTest().callBackRequest(context, success = {
//
//                }, fail = {
//
//                })
    }

    inline fun testNet(context: Context, baseUrl: String,
                       crossinline success: (body: ResponseBody) -> Unit,
                       crossinline fail: (e: Throwable) -> Unit) {
//        val obable = RetrofitManager.getNewInstance(baseUrl)
//                .getApiService()
//                .login()
        val obable = requestBody().login()

        obable.callBackRequest(context, success, fail)

    }

}