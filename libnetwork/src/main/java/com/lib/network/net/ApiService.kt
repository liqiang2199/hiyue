package com.lib.network.net

import com.lib.network.been.DateJsonBeen
import com.lib.network.ui.activity.BaseBeen
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiService {

    @POST("deviceFault/maintenance/lockOrUnlockDevice?code=4&deviceClass=123456&deviceSnId=123456&type=5")
    fun login(): Observable<ResponseBody>

    @GET
    fun home(): Observable<ResponseBody>
    //fun home(): Deferred<ResponseBody>

    @GET("interface/request.do")
    fun getTest(): Observable<ResponseBody>

//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @POST("interface/request.do")
//    fun ping(@Body requestBeen: DateJsonBeen): Observable<ResponseBody>

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("interface/request.do")
    fun commonRequest(@Field("device") device: String
                      , @Field("verison") verison: String
                      , @Field("params") params: String): Observable<ResponseBody>
}