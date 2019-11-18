package com.lib.network.app

import android.app.Application

/**
 * Application
 */
open class BaseApplication : Application() {
    companion object {
        private var instance = BaseApplication()

        fun getInstance(): BaseApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}