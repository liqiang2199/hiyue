package com.hiyue.provider.ext

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.hiyue.provider.R
import com.hiyue.provider.utils.TextWatcherUtil
import com.lib.network.app.BaseApplication
import com.lib.network.db.ParamsDaoDB


fun getParamsDb(): ParamsDaoDB {
    return ParamsDaoDB(BaseApplication.getInstance().applicationContext)
}

/**
 * 判断是否可以点击
 */
fun EditText.enAbleBut(but: Button) {
    this.addTextChangedListener(object : TextWatcherUtil() {
        override fun afterTextChanged(s: Editable?) {
            var str = s.toString().trim()
            if (str.isNoNullOrEmpty()) {
                but.isEnabled = true
                but.setBackgroundResource(R.drawable.shape_but_click)
            } else {
                but.isEnabled = false
                but.setBackgroundResource(R.drawable.shape_no_but)
            }
        }
    })
}

/**
 * 默认不可点击
 */
fun Button.enAbleDefaultFalse() {
    this.isEnabled = false
    this.setBackgroundResource(R.drawable.shape_no_but)
}

fun Button.enAbleDefaultTrue() {
    this.isEnabled = true
    this.setBackgroundResource(R.drawable.shape_but_click)
}

/**
 * String 资源文件
 */
fun Context.reStr(res: Int): String {
    return resources.getString(res)
}

/**
 * 字符串不为空
 */
fun String?.isNoEmptyAndNull(): Boolean {
    return !TextUtils.isEmpty(this) && !this.equals("null")
}