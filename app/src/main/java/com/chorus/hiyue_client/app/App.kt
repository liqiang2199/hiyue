package com.chorus.hiyue_client.app

import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.network.app.BaseApplication

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        MultiDex.install(this)
    }
}