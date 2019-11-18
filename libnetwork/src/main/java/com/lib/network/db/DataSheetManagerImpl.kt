package com.lib.network.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DataSheetManagerImpl(var context: Context) : DataSheetManager {


    private var db: SQLiteDatabase? = null
    private var sqlDB: SQLiteHelper? = null

    init {
        sqlDB = SQLiteHelper.instance(context)
    }

    /**
     * 添加数据
     */
    override fun add(key: String, value: String) {
        //如果有就更新
        if (find(key)) {
            upData(key, value)
            return
        }
        db = sqlDB?.writableDatabase
        if (db == null) {
            return
        }
        val sql = "insert into system_sql (save_key,save_value) values (?,?)"

        try {
            db?.execSQL(sql, arrayOf(key, value))
        } catch (e: Exception) {
            throw SQLParamException("Add failed, Exception is $e")
        } finally {
            db?.close()
        }
    }

    /**
     * 插入数据
     */
    override fun insert(key: String?, value: String?) {
        add(key!!, value!!)
    }

    /**
     * 更新数据
     */
    override fun upData(key: String?, value: String?) {
        db = sqlDB?.readableDatabase
        if (db == null) {
            return
        }
        val sql = "update system_sql set save_value='$value' where save_key='$key'"
        try {
            db?.execSQL(sql)
        } catch (e: Exception) {
            throw SQLParamException("UpData failed, Exception is $e")
        } finally {
            db?.close()
        }
    }

    /**
     * 清空数据
     */
    override fun clearData(key: String?) {
        db = sqlDB?.writableDatabase
        if (db == null) {
            return
        }
        val sql = "delete from system_sql where save_key = '$key'"
        try {
            db?.execSQL(sql)
        } catch (e: Exception) {
            throw SQLParamException("ClearData failed, Exception is $e")
        } finally {
            db?.close()
        }
    }

    /**
     * 删除表
     */
    override fun deleteSheet() {
        db = sqlDB?.readableDatabase
        if (db == null) {
            return
        }
        val sql = "drop table if exists system_sql"

        try {
            db?.execSQL(sql)
        } catch (e: Exception) {
            throw SQLParamException("DeleteSheet failed, Exception is $e")
        } finally {

            db?.close()
        }
    }

    /**
     * 查询数据
     */
    override fun query(key: String?): String {

        db = sqlDB?.readableDatabase
        if (db == null) {
            return ""
        }
        val sql = "select * from system_sql where save_key = ?"
        var valueStr = ""
        var cursor: Cursor? = null
        try {
            cursor = db!!.rawQuery(sql, arrayOf(key))
            if (cursor.moveToNext()) {
                valueStr = cursor.getString(cursor.getColumnIndex("save_value"))
            }
        } catch (e: Exception) {
            throw SQLParamException("Query failed, Exception is $e")
        } finally {
            cursor?.close()

            db!!.close()
        }

        return valueStr
    }

    /**
     * 是否存在
     */
    override fun find(key: String?): Boolean {

        db = sqlDB?.readableDatabase
        if (db == null) {
            return false
        }
        val sql = "select * from system_sql where save_key = ?"
        var cursor: Cursor? = null
        var valueFlag = false

        try {
            cursor = db!!.rawQuery(sql, arrayOf(key))
            valueFlag = cursor.moveToNext()
        } catch (e: Exception) {
            throw SQLParamException("Find failed, Exception is $e")
        } finally {
            cursor?.close()

            db!!.close()
        }

        return valueFlag
    }
}