package com.lib.network.net

import android.accounts.NetworkErrorException
import android.content.Context
import com.lib.network.net.exception.ResponseCodeException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class BaseObserver : Observer<ResponseBody> {

    private var mContext: Context? = null

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        RxApiCancelManager.addDisposable(d)
        onRequestStart()
    }

    override fun onNext(t: ResponseBody) {
        onRequestEnd()
        try {
            if (t != null) {
                onSuccess(t)
            } else {
                onCodeError(ResponseCodeException("is not Successful ,current code=${t.string()}")
                        , 12, "")
            }
        } catch (e: Exception) {
            onCodeError(e, 10, "")
        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            if (e is ConnectException
                    || e is TimeoutException
                    || e is NetworkErrorException
                    || e is UnknownHostException) {
                onFailure(e, true)
            } else {
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            onFailure(e, false)
        }

    }

    abstract fun onSuccess(body: ResponseBody)

    /**
     * e 错误
     * isNetWorkError 是否是网络错误
     */
    abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

    abstract fun onCodeError(e: Throwable, code: Int, msg: String)

    /**
     * 上下文
     */
    fun setMContext(context: Context) {
        this.mContext = context
    }

    fun getMContext(): Context? {
        return mContext
    }

    /**
     * 请求开始
     * 加载显示Loading
     */
    protected fun onRequestStart() {}

    /**
     * 请求结束
     * 关闭Loading
     */
    protected fun onRequestEnd() {}

}