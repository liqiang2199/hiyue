package com.lib.network.ui.activity

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lib.network.ui.BaseActivity
import com.lib.network.ui.common.RefreshViewCommon
import com.lib.network.ui.common.protocol.RefreshControl

/**
 * ListView 下拉刷新 上拉加载
 */
abstract class BaseListActivityNew<D, H : BaseViewHolder>
    : BaseActivity(), RefreshControl<D, H> {

    private var refreshView: RefreshControl<D, H>? = null

    /**
     * 初始化
     */
    override fun initView() {

        getRefreshViewProtocol()

        setInitAdapter(initAdapter())
        initRefreshListener({
            getRefreshData()
        },{
            getLoadMoreData()
        })
    }

    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

    private fun getRefreshViewProtocol(): RefreshControl<D, H> {
        if (refreshView == null) {
            refreshView = RefreshViewCommon(this)
        }

        return refreshView!!
    }

    /**
     * 适配器设置
     */
    abstract fun initAdapter(): BaseQuickAdapter<D, H>

    /**
     * 下来刷新
     */
    abstract fun getRefreshData()

    /**
     * 上拉加载
     */
    abstract fun getLoadMoreData()


    /**
     * 设置列表为空
     */
    override fun setEmpty(res: Int) {
        getRefreshViewProtocol().setEmpty(res)
    }

    /**
     * 设置适配器
     */
    override fun setInitAdapter(adapter: BaseQuickAdapter<D, H>) {
        getRefreshViewProtocol().setInitAdapter(adapter)
    }

    /**
     * 设置数据
     */
    override fun setListData(listData: List<D>) {
        getRefreshViewProtocol().setListData(listData)
    }

    /**
     * 自动刷新
     */
    override fun autoRefresh() {
        getRefreshViewProtocol().autoRefresh()
    }

    /**
     * 是否下拉刷新
     */
    override fun enableRefresh(isRefresh: Boolean) {
    }

    /**
     * 是否需要加载更多
     */
    override fun enableLoadMore(isLoadMore: Boolean) {
    }

    /**
     * 控制 下拉刷新  上拉加载
     */
    override fun enableRefreshAndLoadMore(enable: Boolean) {
    }

    /**
     * 初始化 刷新监听
     */
    override fun initRefreshListener(refreshDate: () -> Unit, loadMoreData: () -> Unit) {
        getRefreshViewProtocol().initRefreshListener(refreshDate, loadMoreData)
    }


}