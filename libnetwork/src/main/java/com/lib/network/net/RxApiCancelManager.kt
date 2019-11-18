package com.lib.network.net

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * RxJava 通过对 CompositeDisposable 管理 取消请求
 */
object RxApiCancelManager {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun addDisposable(disposable: Disposable) {
        if (disposable == null) return
        compositeDisposable.add(disposable)
    }

    fun cancel(disposable: Disposable) {
        if (disposable == null) {
            return
        }
        compositeDisposable.delete(disposable)
    }

    fun cancelAll() {
        compositeDisposable.clear()
    }

}