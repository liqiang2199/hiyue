package com.hiyue_client.usercenter.net

import com.hiyue_client.usercenter.been.SendSmsBeen
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    /**
     * 发送短信验证码  sms_send
     * 短信类别
    login/register/findpass/bind/change_bind
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("interface/request.do")
    fun smsSend(@Body userBeen: SendSmsBeen): Observable<ResponseBody>
}