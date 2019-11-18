package com.lib.network.ui.common

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lib.network.R
import com.lib.network.ui.common.protocol.RefreshControl
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import ezy.ui.layout.LoadingLayout
import org.jetbrains.anko.find

/**
 * 刷新控件
 */
class RefreshViewCommon<D, H : BaseViewHolder>(var activity: Activity)
    : RefreshControl<D, H> {

    private var mRefresh: SmartRefreshLayout? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: BaseQuickAdapter<D, H>? = null
    private var frame_loading: LoadingLayout? = null

    //判断是否是下拉刷新
    private var isRefresh = false

    init {
        initRefreshView()
    }

    /**
     * 界面刷新控件初始化
     */
    private fun initRefreshView() {
        mRefresh = activity.find(R.id.refresh)
        mRefresh!!.setRefreshHeader(ClassicsHeader(activity))
        mRefresh!!.setRefreshFooter(ClassicsFooter(activity))
        mRecyclerView = activity.find(R.id.lv)
        //线性布局
        val linearLayoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = linearLayoutManager

        frame_loading = activity.find(R.id.frame_loading)
    }

    override fun setEmpty(res: Int) {
        frame_loading?.let {
            setEmpty(res)
        }
    }

    /**
     * 设置适配器
     */
    override fun setInitAdapter(adapter: BaseQuickAdapter<D, H>) {
        mAdapter = adapter
        mRecyclerView?.let {
            it.adapter = mAdapter
        }
    }

    /**
     * 设置数据
     */
    override fun setListData(listData: List<D>) {
        //自动刷新界面
        if (isRefresh) {
            mAdapter!!.setNewData(listData)
        } else {
            mAdapter!!.addData(listData)
        }

        //如果没有数据 就直接显示配置的空数据界面
        if (mAdapter?.data?.size == 0) {
            frame_loading?.showEmpty()
        } else {
            frame_loading?.showContent()
        }
        //结束刷新
        mRefresh?.let {
            it.finishRefresh()
            it.finishLoadMore()
        }
        isRefresh = false
    }

    override fun autoRefresh() {
        mRefresh?.let {
            it.autoRefresh()
        }
    }

    override fun enableRefresh(isRefresh: Boolean) {
        mRefresh?.let {
            it.setEnableRefresh(isRefresh)
        }
    }

    override fun enableLoadMore(isLoadMore: Boolean) {
        mRefresh?.let {
            it.setEnableLoadMore(isRefresh)
        }
    }

    override fun enableRefreshAndLoadMore(enable: Boolean) {
        enableRefresh(enable)
        enableLoadMore(enable)
    }

    override fun initRefreshListener(refreshDate: () -> Unit, loadMoreData: () -> Unit) {
        mRefresh?.let {
            it.setOnRefreshListener {
                isRefresh = true
                refreshDate()
            }
            it.setOnLoadMoreListener {
                isRefresh = false
                loadMoreData()
            }
        }
    }
}