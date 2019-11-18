package com.lib.network.mvp.model

import com.lib.network.mvp.handler.BaseHandler

interface IBaseModel<H : BaseHandler> {
    //fun getHttp()
    fun setModelHandler(modelHandler: H)

    fun getModelHandler(): H

}