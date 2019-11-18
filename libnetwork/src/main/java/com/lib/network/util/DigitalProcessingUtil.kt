package com.lib.network.util

import android.text.TextUtils
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * 数字处理
 * 保留两位
 */
object DigitalProcessingUtil {

    private val numberFormat = NumberFormat.getNumberInstance()

    /**
     * 保留两位小数 (四舍五入)
     */
    fun saveTwoDecimalPlaces(dec: Double): String {
        val decimalFormat = DecimalFormat("#.00")
        return decimalFormat.format(dec)
    }

    /**
     * 保留两位小数 (向下取后两位小数)
     */
    fun saveTwoDecimalPlacesFloor(dec: Double): String {
        val decimalFormat = DecimalFormat("#.00")
        decimalFormat.maximumFractionDigits = 2
        decimalFormat.roundingMode = RoundingMode.FLOOR
        return decimalFormat.format(dec)
    }

    /**
     * 显示格式 如 123,123.23
     * (向下取后两位小数)
     */
    fun saveTwoNumberPlacesFloor(dec: Double): String {
        numberFormat.maximumFractionDigits = 2
        numberFormat.roundingMode = RoundingMode.FLOOR
        return numberFormat.format(dec)
    }

    /**
     * 字符串处理
     */
    fun saveTwoDecimalPlacesStr(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return ""
        }
        val dec = str.toDouble()
        return saveTwoDecimalPlaces(dec)
    }

    /**
     * 字符串处理
     */
    fun saveTwoDecimalPlacesStrFloor(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return ""
        }
        val dec = str.toDouble()
        return saveTwoDecimalPlacesFloor(dec)
    }

    /**
     * 字符串处理
     */
    fun saveTwoNumberPlacesStrFloor(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return ""
        }
        val dec = str.toDouble()
        return saveTwoNumberPlacesFloor(dec)
    }

}