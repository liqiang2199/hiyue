package com.hiyue.provider.ext

import android.text.TextUtils

/**
 * 不为空
 */
fun String?.isNoNullOrEmpty(): Boolean {

    return !TextUtils.isEmpty(this) && !this.equals("null")
}