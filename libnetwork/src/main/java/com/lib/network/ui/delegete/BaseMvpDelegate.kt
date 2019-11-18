package com.lib.network.ui.delegete

import android.os.Bundle
import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView

class BaseMvpDelegate<V : IBaseView, P : BasePresenter<V>> : IActivityDelegate<V, P> {

    private var proxyMvpCallBack: ProxyMvpCallBack<V, P>

    constructor(mvpCallBack: MvpCallBack<V, P>) {
        proxyMvpCallBack = ProxyMvpCallBack(mvpCallBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyMvpCallBack?.createPresenter()
        proxyMvpCallBack?.createView()
        proxyMvpCallBack?.attachView()
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        proxyMvpCallBack?.detachVeiw()
    }
}