package com.lib.network.mvp.view

/**
 * 界面UI 控制 （如数据加载，控件）
 */
interface IBaseView {
    fun showLoading()
    fun hideLoading()
    fun onError()
}