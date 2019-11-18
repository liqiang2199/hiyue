package com.hiyue.provider.net

import com.example.extras.Utils
import com.hiyue.provider.been.RequestBeen
import com.hiyue.provider.encrypt.EncryptUtils
import com.hiyue.provider.utils.StringUtil

/**
 * 请求签名工具
 */
object NetEncrypt {

    /**
     * 获取到当前时间 签名
     */
    fun httpEncrypt(urlName: String, requestBeen: RequestBeen): String {

        //val time = TimeManagerUtil.getCurrentDate()
        val time = Utils.dateToStamp(Utils.getSystemDate())
        //时间 后6位
        val after6 = time.substring(time.length - 6)
        val md5Str = EncryptUtils.md5(after6)
        val str1 = md5Str.substring(8, 23)
        val md5Str2 = EncryptUtils.md5(str1)

        println("md5Str2= ${md5Str2.substring(19, 20)}")
        val str8 = StringUtil.substringUtil(md5Str2, 7, 1)
        val str19 = StringUtil.substringUtil(md5Str2, 18, 1)
        val str23 = StringUtil.substringUtil(md5Str2, 22, 1)
        val str28 = StringUtil.substringUtil(md5Str2, 27, 1)


        //val requestBeen = RequestBeen()
        var check_code = EncryptUtils.md5(EncryptUtils.md5(time).substring(str8))
        check_code += EncryptUtils.md5(requestBeen.i.substring(str19))

        check_code += EncryptUtils.md5(EncryptUtils.md5(requestBeen.v).substring(str23))
        check_code = EncryptUtils.md5(EncryptUtils.md5(check_code).substring(str28))

        requestBeen.m = urlName
        requestBeen.t = Utils.getSystemDate()

        requestBeen.c = check_code
        return check_code
    }

    //返回校验码

    fun getCodeCheck(deviceUnixTime: String, deviceInfo: String, deviceVersion: String): String {
        val t1 = EncryptUtils.md5(EncryptUtils.md5(
                deviceUnixTime.substring(deviceUnixTime.length - 6)).substring(8, 23))

        val n8 = Integer.valueOf(t1.substring(7, 8), 16)
        val n19 = Integer.valueOf(t1.substring(18, 19), 16)
        val n23 = Integer.valueOf(t1.substring(22, 23), 16)
        val n28 = Integer.valueOf(t1.substring(27, 28), 16)

        var checkCode = EncryptUtils.md5(EncryptUtils.md5(deviceUnixTime).substring(n8))
        checkCode += EncryptUtils.md5(deviceInfo.substring(n19))
        checkCode += EncryptUtils.md5(EncryptUtils.md5(deviceVersion).substring(n23))
        checkCode = EncryptUtils.md5(EncryptUtils.md5(checkCode).substring(n28))

        return checkCode
    }
}