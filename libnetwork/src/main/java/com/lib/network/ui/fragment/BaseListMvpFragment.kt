package com.lib.network.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.lib.network.mvp.presenter.BasePresenter
import com.lib.network.mvp.view.IBaseView
import com.lib.network.ui.delegete.BaseMvpDelegate
import com.lib.network.ui.delegete.IActivityDelegate
import com.lib.network.ui.delegete.MvpCallBack

/**
 * MVP  List 的 Activity
 */
abstract class BaseListMvpFragment<V : IBaseView, P : BasePresenter<V>, D, H : BaseViewHolder>
    : BaseListFragment<D, H>(), IBaseView, MvpCallBack<V, P> {

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

}