package com.lib.network.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lib.network.R
import com.lib.network.app.ActivityManager
import com.lib.network.net.RxApiCancelManager

/**
 * 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleBar(R.color.white)
        setContentView(initLayoutView())
        ActivityManager.getInstance().addActivity(this)

        if (intent != null) {
            initIntentData(intent)
        }
        initViewInstanceState(savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁后取消当前界面的所有请求
        RxApiCancelManager.cancelAll()
        ActivityManager.getInstance().reMoveActivity(this)
        ActivityManager.getInstance().reMoveChildActivity(this)
    }

    abstract fun initLayoutView(): Int//布局文件
    abstract fun initView()//初始化控件
    abstract fun initIntentData(intent: Intent)//参数传递 接收
    abstract fun initViewInstanceState(savedInstanceState: Bundle?)

    protected fun setTitleBar(color: Int) {
        setTitleBar(color, true)
    }

    protected fun setTitleBar(color: Int, isBlack: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            //状态栏颜色
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(color)
            // 如果亮色，设置状态栏文字为黑色
            if (isBlack) {
                getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    protected fun setTitleBarTRANSPARENT() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT //也可以设置成灰色透明的，比较符合Material Design的风格
        }
    }

    /**
     * 对软键盘管理
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN &&
                currentFocus != null &&
                currentFocus!!.windowToken != null) {

            val v = currentFocus
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationOnScreen(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.rawX > left && event.rawX < right
                    && event.rawY > top && event.rawY < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}