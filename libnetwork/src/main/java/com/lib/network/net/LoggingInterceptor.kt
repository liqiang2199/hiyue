package com.lib.network.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 创建拦截器
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val t1 = System.nanoTime();//请求发起的时间
        Log.v("LoggingInterceptor", String.format("发送请求 %s on %s%n%s",
                request.url(), chain.connection(), request.headers()))

        val response = chain.proceed(request)
        val t2 = System.nanoTime();//收到响应的时间
        //需要创建出一个新的response给应用层处理
//        val responseBody = response.peekBody(1024 * 1024)
//        Log.v("LoggingInterceptor", String.format("接收响应：[%s] %n返回json:%s  %.1fms%n%s",
//                response.request().url(),
//                responseBody.string(),
//                (t2 - t1) / 1e6,
//                response.headers()
//        ))
        return response
    }
}