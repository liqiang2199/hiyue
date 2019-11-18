package com.lib.network.db

import android.content.Context

abstract class ParamDaoImpl(context: Context) : ParamDao(context) {

    override fun getValue(key: String): String {
        return if (dataSheetManager == null) {
            ""
        } else {
            dataSheetManager!!.query(key)
        }
    }

    override fun setValue(key: String, value: String) {
        if (dataSheetManager != null) {
            dataSheetManager!!.add(key, value)
        }
    }

    override fun clearValue(key: String) {
        if (dataSheetManager != null) {
            dataSheetManager!!.clearData(key)
        }
    }

    override fun contains(key: String) {
        if (dataSheetManager != null) {
            dataSheetManager!!.find(key)
        }
    }
}