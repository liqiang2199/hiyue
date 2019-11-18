package com.lib.network.ui.common.protocol

interface RefreshViewProtocol {

    fun setEmpty(res: Int)
    fun autoRefresh()
    fun enableRefresh(isRefresh: Boolean)
    fun enableLoadMore(isLoadMore: Boolean)
    fun enableRefreshAndLoadMore(enable: Boolean)
    fun initRefreshListener(refreshDate: () -> Unit, loadMoreData: () -> Unit)
}