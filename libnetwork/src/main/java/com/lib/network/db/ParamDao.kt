package com.lib.network.db

import android.content.Context

/**
 * 获取数据抽象类
 * 如果数据其他处理 将其实现
 */
abstract class ParamDao(var context: Context) {

    protected var dataSheetManager: DataSheetManager? = null

    init {
        dataSheetManager = DataSheetManagerImpl(context)
    }

    /**
     * key 获取 value
     */
    abstract fun getValue(key: String): String

    /**
     * 设置 key value
     */
    abstract fun setValue(key: String, value: String)

    /**
     * 清空 value
     */
    abstract fun clearValue(key: String)

    /**
     * 查询是否存在 key
     */
    abstract fun contains(key: String)

    /**
     * boolean
     */
    abstract fun setBooleanValue(key: String, value: Boolean)

    abstract fun getBooleanValue(key: String): Boolean

    /**
     * int
     */
    abstract fun setIntegerValue(key: String, value: Int)

    abstract fun getIntegerValue(key: String): Int

}