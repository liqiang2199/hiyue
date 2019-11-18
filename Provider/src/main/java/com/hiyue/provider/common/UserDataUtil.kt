package com.hiyue.provider.common

import com.hiyue.provider.ext.getParamsDb

object UserDataUtil {

    /**
     * 清空 用户数据
     */
    fun userClearnData() {

        getParamsDb().setValue(ConstantUtil.USER_TOKEN, "")
        getParamsDb().setValue(ConstantUtil.USER_MOBILE, "")
        getParamsDb().setValue(ConstantUtil.USER_NAME, "")
        getParamsDb().setValue(ConstantUtil.USER_HEAD_IMAGE, "")
    }
}