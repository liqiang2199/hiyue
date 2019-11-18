package com.lib.network.ui.delegete

import android.util.Log
import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView

/**
 * 代理管理创建
 */
class ProxyMvpCallBack<V : IBaseView, P : BasePresenter<V>>(var mvpCallBack: MvpCallBack<V, P>)
    : MvpCallBack<V, P> {

    override fun createPresenter(): P {
        var presenter: P = mvpCallBack.createPresenter()
        if (presenter == null) {
            presenter = getPresenter()
        }
        if (presenter == null) {
            throw  NullPointerException("Presenter Is Null")
        }
        mvpCallBack.setPresenter(presenter)
        return presenter
    }

    override fun createView(): V {
        var view: V = getMvpView()
        if (view == null) {
            view = mvpCallBack.createView()
        }
        if (view == null) {
            throw  NullPointerException("View Is Null")
        }
        return view
    }

    override fun getPresenter(): P {
        return mvpCallBack.getPresenter()
    }

    override fun setPresenter(presenter: P) = mvpCallBack.setPresenter(presenter)

    override fun getMvpView(): V {
        return mvpCallBack.getMvpView()
    }

    fun attachView() {
        getPresenter().attach(getMvpView())
    }

    fun detachVeiw() {
        getPresenter().detachView()
    }
}