package com.lib.network.db.paramsbase

import android.content.Context
import android.text.TextUtils
import com.lib.network.db.ParamDaoImpl

abstract class ParamDaoBoolean(context: Context) : ParamDaoImpl(context) {

    override fun setBooleanValue(key: String, value: Boolean) {
        if (dataSheetManager == null) {
            return
        }

        dataSheetManager!!.add(key, if (value) {
            "1"
        } else {
            "0"
        })
    }

    override fun getBooleanValue(key: String): Boolean {

        val bool = dataSheetManager?.query(key)

        return !TextUtils.isEmpty(bool) && bool == "1"
    }
}