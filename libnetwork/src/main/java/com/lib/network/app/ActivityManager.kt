package com.lib.network.app

import android.app.Activity
import android.os.Process
import java.util.*

/**
 * 界面管理
 */
class ActivityManager private constructor() {

    private val stackActivity = Stack<Activity>()
    private val stackChildActivity = Stack<Activity>()

    companion object {
        private val instance = ActivityManager()

        fun getInstance(): ActivityManager {
            return instance
        }
    }

    /**
     * 加入栈
     */
    fun addActivity(activity: Activity) {
        stackActivity.push(activity)
    }

    fun addChildActivity(activity: Activity) {
        stackChildActivity.push(activity)
    }

    /**
     * pop 弹出移除界面
     */
    fun popActivity(): Activity {
        return stackActivity.pop()
    }

    /**
     * 移除当前界面
     */
    fun reMoveActivity(activity: Activity) {
        stackActivity.remove(activity)
    }

    fun reMoveChildActivity(activity: Activity) {
        stackChildActivity.remove(activity)
    }

    /**
     * peek 弹出但不移除界面
     */
    fun peekActivity(): Activity {
        return stackActivity.peek()
    }

    /**
     * 获取栈顶Activity
     */
    fun currentActivity(): Activity {
        return stackActivity.lastElement()
    }

    /**
     * 清空栈
     */
    fun cleanStackActivity() {
        for (activity in stackActivity) {
            activity.finish()
        }
        stackActivity.clear()
    }

    fun cleanChildStackActivity() {
        for (activity in stackChildActivity) {
            activity.finish()
        }
        stackChildActivity.clear()
    }

    /**
     * 退出APP
     */
    fun exitApp() {
        stackActivity.clear()
        Process.killProcess(Process.myPid())
        System.exit(0)
    }
}