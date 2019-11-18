package com.chorus.hiyue_client.wxapi

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class WXEntryActivity : AppCompatActivity() {


    private var mContext: Context? = null
//    IWXAPIEventHandler
//    private var mWxApi: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        //这句没有写,是不能执行回调的方法的
        //MyApplication.mWxApi.handleIntent(getIntent(), this)
    }

    private fun registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
//        mWxApi = WXAPIFactory.createWXAPI(this, ConstantUtil.APP_WEIXIN_APPID, false)
//        // 将该app注册到微信
//        mWxApi?.registerApp(ConstantUtil.APP_WEIXIN_APPID)
    }


//    // 微信发送请求到第三方应用时，会回调到该方法
//    override fun onReq(baseReq: BaseReq) {
//
//    }
//
//    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
//    //app发送消息给微信，处理返回消息的回调
//    override fun onResp(baseResp: BaseResp) {
//        Log.i(TAG, "onResp:------>")
//        Log.i(TAG, "error_code:---->" + baseResp.errCode)
//        val type = baseResp.type //类型：分享还是登录
//        when (baseResp.errCode) {
//            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
//                //用户拒绝授权
//                //ToastUtils.showToast(mContext, "拒绝授权微信登录")
//                //用户取消
//                var message = ""
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    message = "取消了微信登录"
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    message = "取消了微信分享"
//                }
//                //ToastUtils.showToast(mContext, message)
//            }
//            BaseResp.ErrCode.ERR_USER_CANCEL -> {
//                var message = ""
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    message = "取消了微信登录"
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    message = "取消了微信分享"
//                }
//                //ToastUtils.showToast(mContext, message)
//            }
//            BaseResp.ErrCode.ERR_OK ->
//                //用户同意
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    //用户换取access_token的code，仅在ErrCode为0时有效
//                    val code = (baseResp as SendAuth.Resp).code
//                    Log.i(TAG, "code:------>$code")
//
//                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
//                    //WXLoginUtils().getWXLoginResult(code, this)
//
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    //ToastUtils.showToast(mContext, "微信分享成功")
//                }
//        }
//    }

    companion object {
        private val TAG = "WXEntryActivity"
        private val RETURN_MSG_TYPE_LOGIN = 1 //登录
        private val RETURN_MSG_TYPE_SHARE = 2 //分享
    }
}