package com.lib.network.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Toast 弹窗类
 */
@SuppressLint("ShowToast")
object ToastUtil {

    private var mToast: Toast? = null

    /**
     * Long Toast
     */
    fun Toast_LongUtil(context: Context, msg: String) {

        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(msg)
            mToast!!.duration = Toast.LENGTH_LONG
        }
        mToast!!.show()
    }

    /**
     * Short Toast
     */
    fun Toast_ShortUtil(context: Context, msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(msg)
            mToast!!.duration = Toast.LENGTH_SHORT
        }
        mToast!!.show()
    }
}