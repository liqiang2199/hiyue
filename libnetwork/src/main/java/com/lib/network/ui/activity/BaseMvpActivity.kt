package com.lib.network.ui.activity

import android.os.Bundle
import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView
import com.lib.network.ui.BaseActivity
import com.lib.network.ui.delegete.BaseMvpDelegate
import com.lib.network.ui.delegete.IActivityDelegate
import com.lib.network.ui.delegete.MvpCallBack
import com.lib.network.util.ToastUtil

abstract class BaseMvpActivity<V : IBaseView, P : BasePresenter<V>> : BaseActivity()
        , IBaseView, MvpCallBack<V, P> {

    private var presenter: P? = null
    private var mvpDelegate: IActivityDelegate<V, P>? = null


    override fun initViewInstanceState(savedInstanceState: Bundle?) {
        getMVPDelegate().onCreate(savedInstanceState)
    }

    private fun getMVPDelegate(): IActivityDelegate<V, P> {
        if (mvpDelegate == null) {
            mvpDelegate = BaseMvpDelegate(this)
        }
        return mvpDelegate!!
    }

    /**
     * 初始化 P 层
     */
    override fun setPresenter(presenter: P) {
        this.presenter = presenter
    }

    override fun createPresenter(): P {
        return presenter!!
    }

    override fun getPresenter(): P {
        return presenter!!
    }

    override fun getMvpView(): V {
        return this as V
    }

    override fun createView(): V {
        return this as V
    }


    override fun onDestroy() {
        super.onDestroy()
        getMVPDelegate().onDestroy()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun onError() {

    }

}