package com.example.extras

import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*



object Utils {

    /**
     * 判断是否为手机号码
     */
    fun isMobile(mobile: String): Boolean {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，
        // "[5,7,9]"表示可以是5,7,9中的任意一位,
        // [^4]表示除4以外的任何一个,
        // \\d{9}"代表后面是可以是0～9的数字，有9位。
        val telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"
        return if (TextUtils.isEmpty(mobile))
            false
        else
            mobile.matches(telRegex.toRegex())
    }


    // 获取软件版本名
    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName
    }

    // 获取软件版本号
    fun getVersionCode(context: Context): Int {
        var versionCode = 0
        try {
            versionCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionCode
    }

    // 获取当前系统日期 yyyy-MM-dd HH:mm:ss
    fun getSystemDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val date = Date()
        return format.format(date)
    }

    /*
     * 将时间转换为时间戳
     */
    fun dateToStamp(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = simpleDateFormat.parse(s)
        val ts = date.time
        res = (ts / 1000).toInt().toString()
        return res
    }

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }

    fun getRandomString(length: Int) : String {
        val keyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray()
        var sb = StringBuffer()
        val len = keyString.count()
        for (i: Int in 0 until length) {
            sb.append(keyString[Math.round(Math.random() * (len - 1)).toInt()])
        }
        return sb.toString()
    }


    fun convertStringToTreeMap(string: String): TreeMap<String, String> {
        val map = createSortedTreeMap()
        if (string.isEmpty()) return map
        val jsonObject = JSONObject(string)
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = jsonObject.getString(key)
            map[key] = value
        }
        return map
    }

    fun convertTreeMapToString(map: TreeMap<String, String>): String {
        return map.toString()
    }

    fun convertTreeMapToJsonString(map: TreeMap<String, String>): String {
        val jsonObject = JSONObject(map)
        return jsonObject.toString()
    }

    /**
     * 创建自动排序的 TreeMap
     */
    fun createSortedTreeMap(): TreeMap<String, String> {
        return TreeMap<String, String>(
                Comparator<String> { obj1, obj2 ->
                    obj1.compareTo(obj2)
                })
    }


}