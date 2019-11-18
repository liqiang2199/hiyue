package com.lib.network.ui.delegete

import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView

/**
 * 目标代理获取 V P
 */
interface MvpCallBack<V : IBaseView, P : BasePresenter<V>> {
    fun createPresenter(): P
    fun createView(): V
    fun getPresenter(): P
    fun setPresenter(presenter: P)
    fun getMvpView(): V
}