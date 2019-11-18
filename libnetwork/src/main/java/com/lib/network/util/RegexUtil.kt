package com.lib.network.util

import java.util.regex.Pattern

object RegexUtil {

    fun httpReges(str: String): Boolean {
        val pattern = "^http$"
        val matcher = Pattern.compile(pattern).matcher(str)
        return matcher.find()
    }

    fun httpsReges(str: String): Boolean {
        val pattern = "^https$"
        val matcher = Pattern.compile(pattern).matcher(str)
        return matcher.find()
    }

    /**
     * 匹配存数字
     */
    fun intRegex(str: String): Boolean {
        val pattern = "^\\d+$"
        val matcher = Pattern.compile(pattern).matcher(str)
        return matcher.find()
    }
}