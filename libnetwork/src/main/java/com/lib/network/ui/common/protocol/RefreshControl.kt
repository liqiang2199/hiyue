package com.lib.network.ui.common.protocol

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


interface RefreshControl<D, H : BaseViewHolder> : RefreshViewProtocol {

    fun setInitAdapter(adapter: BaseQuickAdapter<D, H>)
    fun setListData(listData: List<D>)
}