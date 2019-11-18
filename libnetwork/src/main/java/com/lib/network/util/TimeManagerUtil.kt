package com.lib.network.util

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间转换管理
 */
@SuppressLint("SimpleDateFormat")
object TimeManagerUtil {
    val YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss"
    val HHMMSS = "HH:mm:ss"
    val YYMMDD_HHMM = "yyyy-MM-dd HH:mm"
    val HHMM = "HH:mm"
    val YYMMDD = "yyyy-MM-dd"
    val FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm"
    val FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss"

    private val YEAR = 365 * 24 * 60 * 60// 年
    private val MONTH = 30 * 24 * 60 * 60// 月
    private val DAY = 24 * 60 * 60// 日
    private val HOUR = 60 * 60// 小时
    private val MINUTE = 60// 分钟


    private var calendar: Calendar?  = null
    private var formatter: SimpleDateFormat? = null
    /**
     * 获取当前时间（日期＋时间）
     * 默认 yyyy-MM-dd HH:mm:ss
     * @return String
     */
    fun getCurrentDate(pattern: String=YYMMDD_HHMMSS):String{
        var timeDate = ""
        try {
            calendar = GregorianCalendar()
            formatter = SimpleDateFormat(pattern)
            timeDate = formatter!!.format(calendar!!.time)
        }catch (e:Exception){

        }
        return timeDate
    }

    /**
     * 根据用户生日计算年龄
     */
    fun getAgeByBirthday(birthday: Date): Int {
        val cal = Calendar.getInstance()

        if (cal.before(birthday)) {
            throw IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!")
        }

        val yearNow = cal.get(Calendar.YEAR)
        val monthNow = cal.get(Calendar.MONTH) + 1
        val dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH)

        cal.time = birthday
        val yearBirth = cal.get(Calendar.YEAR)
        val monthBirth = cal.get(Calendar.MONTH) + 1
        val dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH)

        var age = yearNow - yearBirth

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--
                }
            } else {
                // monthNow>monthBirth
                age--
            }
        }
        return age
    }

    /**
     * 根据时间戳获取描述时间，3分钟前，1天前
     * 仿朋友圈发布时间的显示不同的 时间
     * @param timestamp
     * 时间单位为秒
     * @return 时间字符
     */
    fun getDescriptionTimeFromTimestamp(timestamp: Long): String {
        val currentTime = System.currentTimeMillis() / 1000
        val timeGap = currentTime - timestamp// 与现在时间相差秒
        println("timeGap: " + timeGap)
        var timeStr: String? = null
        if (timeGap > YEAR) {
            timeStr = (timeGap / YEAR).toString() + "年前"
        } else if (timeGap > MONTH) {
            timeStr = (timeGap / MONTH).toString() + "个月前"
        } else if (timeGap > DAY) {// 1天以内
            timeStr = (timeGap / DAY).toString() + "天前"
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = (timeGap / HOUR).toString() + "小时前"
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = (timeGap / MINUTE).toString() + "分钟前"
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚"
        }
        return timeStr
    }

    /**
     * 根据时间戳获取描述时间，3分钟前，1天前
     * 仿朋友圈发布时间的显示不同的 时间
     */
    fun getDescriptionTimeFromTimestampString(timesTamp:String):String{
        if (!TextUtils.isEmpty(timesTamp)){
            return ""
        }
        if (CommonUtil.editIsAllNum(timesTamp)) {
            throw NumberFormatException("please afferent number")
        }
        return getDescriptionTimeFromTimestamp(timestamp = timesTamp.toLong())
    }

    /**
     * 将Date 对象 转为 String
     */
    fun dateToString(data: Date, formatType: String): String {
        return SimpleDateFormat(formatType).format(data)
    }

    /**
     * // long类型转换为String类型
    // currentTime要转换的long类型的时间戳
    // formatType要转换的string类型的时间格式
     */
    fun longToString(currentTime: Long, formatType: String= YYMMDD_HHMMSS): String {
        var strTime = ""
        val date = longToDate(currentTime, formatType)// long类型转成Date类型
        strTime = dateToString(date!!, formatType) // date类型转成String

        return strTime
    }

    fun longToString(currentTime: String,formatType: String= YYMMDD_HHMMSS): String {

        return longToString(currentTime.toLong(),formatType)
    }

    /**
     * 将 字符串转成 date 类型
     * 格式默认为 yyyy-MM-dd HH:mm:ss
     */
    fun stringToDate(strTime: String, formatType: String= YYMMDD_HHMMSS): Date? {
        val formatter = SimpleDateFormat(formatType)
        var date: Date? = null
        try {
            date = formatter.parse(strTime)
        } catch (e: ParseException) {
        }

        return date
    }

    /**
     *  long转换为Date类型
    currentTime要转换的long类型的时间戳
    formatType要转换的时间格式yyyy-MM-dd HH:mm:ss
     */
    fun longToDate(currentTime: Long, formatType: String=YYMMDD_HHMMSS): Date? {
        val dateOld = Date(currentTime) // 根据long类型
        val sDateTime = dateToString(dateOld, formatType) // 把date类型的时间转换为string
        return stringToDate(sDateTime, formatType)
    }

    /**
     * // string类型转换为long类型
    // strTime要转换的Long类型的时间戳
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
     */
    fun stringToLong(strTime: String, formatType: String=YYMMDD_HHMMSS): Long {
        val date = stringToDate(strTime, formatType) // String类型转成date类型
        return if (date == null) {
            0
        } else {
            dateToLong(date)
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间戳
    /**
     * date.time/1000 除去 毫秒数
     */
    private fun dateToLong(date: Date): Long {
        return date.time/1000
    }

    /**
     * startDate 开始时间
     * endDate 结束时间
     * format 时间格式 （结束时间 和开始时间 格式必须一样才能精确计算天数）
     * day 计算的天数
     * 列如：
     * DAY_NUM = 30 * 24 * 60 * 60 * 1000// 计算30天
    DAY_ONE = 24 * 60 * 60 * 1000// 计算是一天
     */
    fun startBetweenEnd(startDate:String,endDate:String,format:String = YYMMDD_HHMMSS,day:Long):Int{
        return (stringToLong(startDate,format)- stringToLong(endDate,format)/(day*24*60*60*1000)).toInt()
    }

    /**
     * 计算出来的天数是否在 这个day天数之内
     */
    fun startBetweenEndWithIn(startDate:String,endDate:String,format:String = YYMMDD_HHMMSS,day:Long =1):Boolean{
        val withInNum = (stringToLong(startDate,format)- stringToLong(endDate,format)/(day*24*60*60*1000))
        return withInNum in 0..day
    }
}