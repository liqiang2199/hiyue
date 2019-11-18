package com.lib.network.util

import android.content.Context
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED


/**
 * 公用类
 */
object CommonUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    //字符串为空判断
    fun StringNull(s: String?): Boolean {
        return s == null || s.isEmpty() || s == "null"

    }

    /**
     * 判断是否是汉字
     * @param inputChar
     * @return
     */
    fun editChineseCharacters(inputChar: String): Boolean {
        return !StringNull(inputChar) && inputChar.matches("[\u4e00-\u9fa5]+".toRegex())
    }

    /**
     * 判断是否是数字
     * @param inputNum
     * @return
     */
    fun editIsAllNum(inputNum: String): Boolean {
        return !StringNull(inputNum) && inputNum.matches("[0-9]+".toRegex())
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    fun hasSdcard(): Boolean {
        return Environment.getExternalStorageState() == (Environment.MEDIA_MOUNTED)
    }

}