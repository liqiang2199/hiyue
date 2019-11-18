package com.lib.network.mvp.presenter

import android.util.Log
import com.lib.network.mvp.model.BaseModel
import com.lib.network.mvp.view.IBaseView
import java.lang.ref.WeakReference

/**
 * 网络请求返回数据 交与View 显示
 * 绑定View 关联Model
 */
abstract class BasePresenter<V : IBaseView> {

    private var mView: WeakReference<V>? = null
    private var mModel: BaseModel<*>? = null

    fun attach(view: V) {
        mView = WeakReference(view)
    }

    fun getView(): V? {
        return mView!!.get()
    }

    fun detachView() {
        if (mView != null) {
            mView!!.clear()
            mView = null
            if (mModel != null){
                mModel!!.detachHandler()
                mModel = null
            }
        }
    }

    fun <M : BaseModel<*>> createModel(v: M): M {
        this.mModel = v
        return v
    }
}