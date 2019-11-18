package com.lib.network.ui

import android.support.v4.app.Fragment
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lib.network.net.RxApiCancelManager

/**
 * Fragment 基类
 */
abstract class BaseFragment : Fragment() {
    private var isVisible: Boolean? = false
    private var isPrepared: Boolean? = false//判断View 控件是否初始化完成

    //布局View
    private var getCreateView: View? = null

    /**
     * 创建
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getCreateView = inflater.inflate(initLayoutRes(), null)

        return getCreateView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(getCreateView!!)
        initViewInstanceState(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            argumentsParam(arguments)
        }

        isPrepared = true
        lazyLoadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁后取消当前界面的所有请求
        RxApiCancelManager.cancelAll()
    }

    /**
     * 判断是否显示
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            //可见
            isVisible = userVisibleHint
            onVisible()
        } else {
            isVisible = false
            onUnVisible()
        }
    }

    /**
     * 可见时调用
     */
    protected fun onVisible() {
        lazyLoadData()
    }

    /**
     * 不可见调用
     */
    protected fun onUnVisible() {

    }

    /**
     * 懒加载方法
     */
    protected abstract fun lazyLoad()

    /**
     * Fragment 传入参数
     */
    protected abstract fun argumentsParam(arguments: Bundle)

    /**
     * 初始化控件
     */
    protected abstract fun initView(view: View)

    /**
     * Fragment 加载控件
     */
    abstract fun initLayoutRes(): Int

    abstract fun initViewInstanceState(savedInstanceState: Bundle?)


    private fun lazyLoadData() {
        if (!isPrepared!! || !isVisible!!) {
            return
        }
        lazyLoad()
    }
}