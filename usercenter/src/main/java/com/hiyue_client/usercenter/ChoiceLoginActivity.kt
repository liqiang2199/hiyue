package com.hiyue_client.usercenter

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.WindowManager
import com.alibaba.fastjson.JSON
import com.example.extras.Utils
import com.hiyue.provider.been.RequestBeen
import com.hiyue.provider.common.ConstantUtil
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.utils.StringUtil
import com.hiyue_client.usercenter.been.SendSmsBeen
import com.hiyue_client.usercenter.ui.LoginActivity
import com.hiyue_client.usercenter.ui.activity.RegisterActivity
import com.lib.network.app.ActivityManager
import com.lib.network.been.DateJsonBeen
import com.lib.network.ext.onClick
import com.lib.network.net.NetWorkApi
import com.lib.network.ui.BaseActivity
import com.lib.network.util.ToastUtil
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
//import com.tencent.mm.opensdk.modelbase.BaseReq
//import com.tencent.mm.opensdk.modelbase.BaseResp
//import com.tencent.mm.opensdk.modelmsg.SendAuth
//import com.tencent.mm.opensdk.openapi.IWXAPI
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
//import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.Tencent
import kotlinx.android.synthetic.main.activity_choice_login.*
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import com.tencent.tauth.UiError
import com.tencent.tauth.IUiListener
import org.json.JSONException


/**
 * 登录界面选择
 */
class ChoiceLoginActivity : BaseActivity() {

    private var key = "iJ9.c#hHbK53fTnw"
    private var iv = "0000000000000000"

    //IWXAPIEventHandler
    var mWxApi: IWXAPI? = null

    override fun initLayoutView(): Int {
        //无title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_TRANSLUCENT_STATUS ,
                WindowManager.LayoutParams. FLAG_TRANSLUCENT_STATUS);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        return R.layout.activity_choice_login
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {

        registerToWX()
        ActivityManager.getInstance().addChildActivity(this)
        butLogin.onClick {
            startActivity<LoginActivity>()
            //md5HttpDate()
            //http()
        }
        butRegister.onClick {
            startActivity<RegisterActivity>()
        }

        ivQQLogin.onClick {
            //QQ登录
            val mTencent = Tencent.createInstance(ConstantUtil.APP_QQ_APPID
                    , this.applicationContext)
            if (!mTencent.isSessionValid) {
                mTencent.login(this, "all", BaseUiListener())
            }
        }
        ivWeiXinLogin.onClick {
            weixinLogin()
        }
    }

    override fun initIntentData(intent: Intent) {
    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {
    }

    private fun registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, ConstantUtil.APP_WEIXIN_APPID, false)
        // 将该app注册到微信
        mWxApi?.registerApp(ConstantUtil.APP_WEIXIN_APPID)
    }

    /**
     * 微信登录
     */
    private fun weixinLogin() {
        val req: SendAuth.Req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_sdk_demo_test"
        mWxApi?.sendReq(req)
    }

    /**
     * QQ登录监听
     */
    private inner class BaseUiListener : IUiListener {
        override fun onComplete(p0: Any?) {
            try {
                val jsonObject = JSONObject(p0.toString())
                val mNickname = jsonObject.getString("nickname")//获取用户QQ名称
                val mGender = jsonObject.getString("gender")//获取用户性别
                val mUserHeader = jsonObject.getString("figureurl_qq_2")//获取用户头像
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        override fun onError(e: UiError) {
            ToastUtil.Toast_ShortUtil(this@ChoiceLoginActivity
                    ,"onError:code:" + e.errorCode + ", msg:"+ e.errorMessage + ", detail:" + e.errorDetail)
        }

        override fun onCancel() {
            ToastUtil.Toast_ShortUtil(this@ChoiceLoginActivity,"onCance")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Tencent.onActivityResultData(requestCode, resultCode, data, BaseUiListener())
    }

    companion object {
        private val TAG = "WXEntryActivity"
        private val RETURN_MSG_TYPE_LOGIN = 1 //登录
        private val RETURN_MSG_TYPE_SHARE = 2 //分享
    }

//    override fun onReq(p0: BaseReq?) {
//
//    }
//
//    override fun onResp(baseResp: BaseResp?) {
//        val type = baseResp?.type //类型：分享还是登录
//        when (baseResp?.errCode) {
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
//                ToastUtil.Toast_ShortUtil(this, message)
//            }
//            BaseResp.ErrCode.ERR_USER_CANCEL -> {
//                var message = ""
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    message = "取消了微信登录"
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    message = "取消了微信分享"
//                }
//                ToastUtil.Toast_ShortUtil(this, message)
//            }
//            BaseResp.ErrCode.ERR_OK ->
//                //用户同意
//                if (type == RETURN_MSG_TYPE_LOGIN) {
//                    //用户换取access_token的code，仅在ErrCode为0时有效
//                    val code = (baseResp as SendAuth.Resp).code
//                    Log.i(ChoiceLoginActivity.TAG, "code:------>$code")
//
//                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
//                    //WXLoginUtils().getWXLoginResult(code, this)
//
//                } else if (type == RETURN_MSG_TYPE_SHARE) {
//                    ToastUtil.Toast_ShortUtil(this, "微信分享成功")
//                }
//        }
//    }

    private fun http() {
        val device = "android"
        val deviceIdentification = "00000000000000000000000000000000"
        val appVersion = "0.01"
        val deviceVersion = "9.0"
        val deviceTime = Utils.getSystemDate()
        val deviceUnixTime = Utils.dateToStamp(deviceTime)
        val postUrl = "https://h.mdeve.com/"

        val t1 = EncryptUtils.md5(EncryptUtils.md5(
                deviceUnixTime.substring(deviceUnixTime.length - 6)).substring(8, 23))

        val n8 = Integer.valueOf(t1.substring(7, 8), 16)
        val n19 = Integer.valueOf(t1.substring(18, 19), 16)
        val n23 = Integer.valueOf(t1.substring(22, 23), 16)
        val n28 = Integer.valueOf(t1.substring(27, 28), 16)

        var checkCode = EncryptUtils.md5(EncryptUtils.md5(deviceUnixTime).substring(n8))
        checkCode += EncryptUtils.md5(deviceIdentification.substring(n19))
        checkCode += EncryptUtils.md5(EncryptUtils.md5(deviceVersion).substring(n23))
        checkCode = EncryptUtils.md5(EncryptUtils.md5(checkCode).substring(n28))

        val sendSmsBeen = SendSmsBeen(receiver = "18081268217", call_index = "register")

        val params = TreeMap<String, String>()
        params["d"] = device
        params["m"] = "sms_send"
        params["t"] = deviceTime
        params["v"] = deviceVersion
        params["i"] = deviceIdentification
        params["e"] = "00000A"
        params["n"] = appVersion
        params["b"] = "1"
        params["c"] = checkCode
        //请求参数全在 params 上面是系统参数 下面是业务参数
        params["mobile"] = sendSmsBeen.receiver!!
        params["category"] = sendSmsBeen.call_index!!

        println("checkCode = $checkCode")
        println("params = $params")
        println("json = ${Utils.convertTreeMapToJsonString(params)}")

        val paramsStringEncrypted = EncryptUtils.AES_Encode(
                Utils.convertTreeMapToJsonString(params), key, iv)

//        val dataJsonBeen = DateJsonBeen()
//        dataJsonBeen.device = device
//        dataJsonBeen.verison = appVersion
//        dataJsonBeen.params = paramsStringEncrypted
        val mapList: MutableMap<String, String> = HashMap()
        mapList.put("device",device)
        mapList.put("verison",appVersion)
        mapList.put("params",paramsStringEncrypted)

        NetWorkApi.testNet(this, device,appVersion,paramsStringEncrypted, string = {
            val json = it.string()
            Log.e("callBacksuccess", " 返回参数 ${EncryptUtils.AES_Decode(json, key, iv)} ")
        })

    }

    private fun md5HttpDate() {
        val time = "2019-06-24 11:12:30"
        //时间 后6位
        val after6 = time.substring(time.length - 6, time.length)
        val md5Str = EncryptUtils.md5(after6)
        val str1 = md5Str.substring(8, 23)
        val md5Str2 = EncryptUtils.md5(str1)

        println("md5Str2= ${md5Str2.substring(19, 20)}")
        println("md5Str2#### = ${Integer.valueOf("f", 16)}")
        val str8 = Integer.valueOf(md5Str2.substring(7, 8), 16)
        val str19 = Integer.valueOf(md5Str2.substring(18, 19), 16)
        val str23 = Integer.valueOf(md5Str2.substring(22, 23), 16)
        val str28 = Integer.valueOf(md5Str2.substring(27, 28), 16)


        val requestBeen = RequestBeen()
        var check_code = EncryptUtils.md5(EncryptUtils.md5(time).substring(str8.toInt()))
        check_code += EncryptUtils.md5(requestBeen.i
                .substring(str19.toInt(), requestBeen.i.length))
        check_code += EncryptUtils.md5(
                EncryptUtils.md5(requestBeen.v).substring(str23.toInt(), requestBeen.v.length))
        check_code = EncryptUtils.md5(
                EncryptUtils.md5(check_code)
                        .substring(str28.toInt()))

        requestBeen.c = check_code
        val jsonData = JSON.toJSONString(requestBeen)

        val dataJsonBeen = DateJsonBeen()
        dataJsonBeen.device = requestBeen.d
        dataJsonBeen.verison = requestBeen.v
        dataJsonBeen.params = EncryptUtils.AES_Encode(jsonData, key, iv)


//        NetWorkApi.testNet(this, dataJsonBeen, string = {
//            Log.e("callBacksuccess", " 返回参数 ${it.string()} ")
//        })

    }

}