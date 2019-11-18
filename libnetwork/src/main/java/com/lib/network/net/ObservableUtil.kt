package com.lib.network.net

import android.content.Context
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import java.net.ConnectException

/**
 * 发送网络请求
 */
inline fun Observable<ResponseBody>.callBackRequest(mContext: Context,
                                                    crossinline success: (body: ResponseBody) -> Unit,
                                                    crossinline fail: (e: Throwable) -> Unit) {
    if (NetWorkUtil.isNetWorkAvailable(mContext)) {
        this
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        RxApiCancelManager.addDisposable(d)
                    }

                    override fun onNext(t: ResponseBody) {
                        success(t)
                    }

                    override fun onError(e: Throwable) {
                        fail(e)
                    }


                })
    } else {
        fail(ConnectException("请检查网路连接"))
    }


}

/**
 * 传入自定义请求回调
 * 实现 BaseObserver
 */
fun Observable<ResponseBody>.callBackRequest(mContext: Context, baseObserver: BaseObserver) {

    baseObserver.setMContext(mContext)

    if (NetWorkUtil.isNetWorkAvailable(mContext)) {
        this
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver)
    } else {
        baseObserver.onFailure(ConnectException("请检查网路连接"), true)
    }

}


fun requestBody(): ApiService {

    return RetrofitManager.getNewInstance()
            .getApiService()
}
