package com.lib.network.mvp.model

import android.util.Log
import com.lib.network.app.BaseApplication
import com.lib.network.db.ParamsDaoDB
import com.lib.network.mvp.handler.BaseHandler
import com.lib.network.net.NetWorkApi
import com.lib.network.net.RetrofitManager

abstract class BaseModel<H : BaseHandler> : IBaseModel<H> {
    //访问列表 页码和页码数量
    protected var pageIndex = 1
    protected var pageSize = 10
    protected val baseApplication = BaseApplication.getInstance().applicationContext

    //发送网络请求

    private var modelHandler: H? = null

    override fun setModelHandler(modelHandler: H) {
        this.modelHandler = modelHandler
    }

    override fun getModelHandler(): H {
        return modelHandler!!
    }

    fun post() {
        val paramsDaoDB = ParamsDaoDB(baseApplication)
        //paramsDaoDB.setBooleanValue("bool1",true);

        Log.e("ParamsDaoDB", "paramsDaoDB bool 111 = " + paramsDaoDB.getBooleanValue("bool1"))
        Log.e("ParamsDaoDB", "paramsDaoDB bool = " + paramsDaoDB.getBooleanValue("bool"))
        Log.e("ParamsDaoDB", "paramsDaoDB ee = " + paramsDaoDB.getBooleanValue("boole"))
        //NetWorkApi.testNet(BaseApplication.getInstance().applicationContext)
    }

    fun detachHandler(){
        if (modelHandler != null) {
            modelHandler = null
        }
    }
}