package com.lib.network.db

import android.app.Activity
import android.content.Context

/**
 * 调用类
 */
class ParamDaoInit {

    fun initParamDao(context: Context): ParamDao {
        return ParamsDaoDB(context.applicationContext)
    }

    fun initParamDao(activity: Activity): ParamDao {
        return ParamsDaoDB(activity.applicationContext)
    }
}