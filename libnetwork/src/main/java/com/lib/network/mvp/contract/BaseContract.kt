package com.lib.network.mvp.contract

import com.lib.network.mvp.view.IBaseView

interface BaseContract {
    interface IContractView : IBaseView{
        fun success(msg: String)
    }
}