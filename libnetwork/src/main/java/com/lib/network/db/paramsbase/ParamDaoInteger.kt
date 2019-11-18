package com.lib.network.db.paramsbase

import android.content.Context
import android.text.TextUtils
import com.lib.network.util.RegexUtil

abstract class ParamDaoInteger(context: Context) : ParamDaoBoolean(context) {

    override fun setIntegerValue(key: String, value: Int) {
        dataSheetManager?.add(key, "$value")
    }

    override fun getIntegerValue(key: String): Int {

        val value = dataSheetManager?.query(key)

        return if (!TextUtils.isEmpty(value) && RegexUtil.intRegex(value!!)) {
            value.toInt()
        } else {
            //如果不存在,就返回-1
            -1
        }
    }
}