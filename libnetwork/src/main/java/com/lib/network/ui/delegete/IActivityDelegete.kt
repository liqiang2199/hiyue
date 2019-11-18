package com.lib.network.ui.delegete

import android.os.Bundle
import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView

/**
 * 界面 MVP  的代理
 * 需要绑定 界面 生命周期
 */
interface IActivityDelegate<V : IBaseView, P : BasePresenter<V>> {

    fun onCreate(savedInstanceState: Bundle?)
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()

}