package com.lib.network.net

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lib.network.app.BaseApplication
import com.lib.network.db.ParamsDaoDB
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor() {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var mContext: Context? = null
    private var baserUrl: String? = null//基本请求地址
    private var isCustomFlag = false//是否是自定义接口（IP）
    private var apiService: ApiService? = null
    private var retrofitManager: RetrofitManager? = null
    private var paramsDaoDB: ParamsDaoDB? = null


    companion object {
        //获取本地对象
        @JvmStatic
        @Synchronized
        fun getInstance(): RetrofitManager {

            return setManagerContext(false)
        }

        @JvmStatic
        fun getNewInstance(): RetrofitManager {

            return setManagerContext(true)
        }

        /**
         * baserUrl 请求地址
         */
        @JvmStatic
        fun getNewInstance(baserUrl: String): RetrofitManager {

            return getNewManager(baserUrl, false)
        }

        @JvmStatic
        fun getNewInstanceHttps(baserUrl: String): RetrofitManager {

            return getNewManager(baserUrl, true)
        }

        /**
         * baserUrl 请求地址
         * isHttps 是否是https 请求
         */
        private fun getNewManager(baserUrl: String, isHttps: Boolean): RetrofitManager {
            val manager = RetrofitBuilder.newInstanceRetrofit()
            manager.mContext = BaseApplication.getInstance()
            //获取 是否是自定义
            manager.paramsDaoDB = ParamsDaoDB(BaseApplication.getInstance())
            manager.isCustomFlag = manager.paramsDaoDB!!.getBooleanValue("isCustomFlag")

            manager.baserUrl = customUrl(manager, baserUrl, isHttps)

            //初始化
            manager.initOkHttp()
            manager.initRetrofit()

            return manager
        }

        /**
         * isNew 是否需要重新实例化一个 Retrofit OKHttp
         */
        private fun setManagerContext(isNew: Boolean): RetrofitManager {


            val manager = if (!isNew) {
                RetrofitBuilder.retrofitManager

            } else {
                RetrofitBuilder.newInstanceRetrofit()
            }
            manager.paramsDaoDB = ParamsDaoDB(BaseApplication.getInstance())
            manager.isCustomFlag = if (manager.paramsDaoDB != null) {
                manager.paramsDaoDB!!.getBooleanValue("isCustomFlag")
            } else {
                false
            }
            manager.baserUrl = customUrl(manager, "", false)

            manager.mContext = BaseApplication.getInstance()
            if (manager.retrofitManager == null || isNew) {
                //初始化
                manager.initOkHttp()
                manager.initRetrofit()
                manager.retrofitManager = manager
            }

            return manager
        }

        /**
         * URL 设置
         * manager 返回RetrofitManager对象
         * baserUrl 请求地址
         * isHttps 是否是https
         */
        private fun customUrl(manager: RetrofitManager, baserUrl: String, isHttps: Boolean): String {
            //读取是否是自定义
            return if (manager.isCustomFlag && !TextUtils.isEmpty(baserUrl)) {
                //如果是自定义IP 就获取自定义IP
                if (manager.paramsDaoDB == null){
                    UrlUtil.customIP = ""
                } else {
                    UrlUtil.customIP = manager.paramsDaoDB!!.getValue("customIP")
                }

                if (isHttps) {
                    UrlUtil.customBaseUrl_Https(UrlUtil.customIP)
                } else{
                    UrlUtil.customBaseUrl_Http(UrlUtil.customIP)
                }
            } else {
                if (!TextUtils.isEmpty(baserUrl) && !baserUrl.contains("http")) {
                    if (isHttps) {
                        UrlUtil.customBaseUrl_Https(baserUrl)
                    } else{
                        UrlUtil.customBaseUrl_Http(baserUrl)
                    }
                } else {
                    baserUrl
                }
            }
        }

    }

    /**
     * 构建静态内部类 实现单列
     */
    private object RetrofitBuilder {
        @SuppressLint("StaticFieldLeak")
        val retrofitManager = RetrofitManager()

        //创建一个新的对象
        fun newInstanceRetrofit(): RetrofitManager {
            return RetrofitManager()
        }
    }

    /**
     * 初始化okhttp
     *
     * @return
     */
    private fun initOkHttp() {
        if (mContext == null) {
            throw IllegalArgumentException("context is empty")
        }
        val cacheDirectory = File("${mContext!!.cacheDir.absolutePath}respones")

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            @Override
            fun log(message: String) {
                Log.e("TTT", "请求参数: $message")
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//如果连接失败尝试重新连接
                .cache(Cache(cacheDirectory, 10 * 1024 * 1024))//设置缓存文件
                //设置okhttp拦截器，这样做的好处是可以为你的每一个retrofit2的网络请求都增加相同的head头信息，
                // 而不用每一个请求都写头信息
                .addInterceptor(LoggingInterceptor())
                .addInterceptor(interceptor)
                .build()
    }

    /**
     * 初始化 Retrofit
     */
    private fun initRetrofit() {
        if (okHttpClient == null) {
            initOkHttp()
        }

        if (TextUtils.isEmpty(baserUrl)) {
            baserUrl = UrlUtil.baseUrl
        }

        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//如果网络访问返回的是json字符串，使用gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//此处顺序不能和上面对调，否则不能同时兼容普通字符串和Json格式字符串  为了支持rxjava,需要添加下面这个把 Retrofit 转成RxJava可用的适配类
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(baserUrl)
                .build()

        setCreate()


    }

    /**
     * 绑定对应的API
     */
    fun <T> setCreate(reqServer: Class<T>): T {
        if (retrofit == null) {
            initRetrofit()
        }
        return retrofit!!.create(reqServer)
    }

    private fun setCreate(): ApiService {
        //初始化接口
        apiService = setCreate(ApiService::class.java)
        return apiService!!
    }

    fun getApiService(): ApiService {

        return apiService!!
    }


}
