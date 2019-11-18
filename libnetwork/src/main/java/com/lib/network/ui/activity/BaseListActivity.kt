package com.lib.network.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lib.network.R
import com.lib.network.common.IPageManager
import com.lib.network.ui.BaseActivity
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import ezy.ui.layout.LoadingLayout
import org.jetbrains.anko.find

abstract class BaseListActivity<D, H : BaseViewHolder> : BaseActivity(),IPageManager {

    protected var mRefresh: SmartRefreshLayout? = null
    protected var mRecyclerView: RecyclerView? = null
    protected var mAdapter: BaseQuickAdapter<D, H>? = null
    private var frame_loading: LoadingLayout? = null

    //protected var listData: MutableList<*>? = null
    //判断是否是下拉刷新
    private var isRefresh = false

    //请求页码
    override var pageIndex: Int = 1
    override val pageSize: Int = 20


    override fun initView() {
        initRefreshView()
    }

    /**
     * activity状态
     */
    override fun initViewInstanceState(savedInstanceState: Bundle?) {

    }

    /**
     * 界面刷新控件初始化
     */
    private fun initRefreshView() {
        mRefresh = find(R.id.refresh)
        mRefresh!!.setRefreshHeader(ClassicsHeader(this))
        mRefresh!!.setRefreshFooter(ClassicsFooter(this))
        mRecyclerView = find(R.id.lv)
        //线性布局
        val linearLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = linearLayoutManager

        frame_loading = find(R.id.frame_loading)
        setAdapter()
        initRefreshListener()
    }

    protected fun setEmpty(res: Int) {
        frame_loading?.let {
            setEmpty(res)
        }
    }

    /**
     * 设置适配器
     */
    private fun setAdapter() {
        mAdapter = initAdapter()
        mRecyclerView?.let {
            it.adapter = mAdapter
        }
    }

    //<T, H : BaseViewHolder>
    abstract fun initAdapter(): BaseQuickAdapter<D, H>

    //加入数据
    protected fun setListData(listData: List<D>) {

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

    /**
     * 是否自动刷新
     */
    protected fun autoRefresh() {
        mRefresh?.let {
            it.autoRefresh()
        }
    }

    /**
     * 是否需要禁止刷新
     */
    protected fun enableRefresh(isRefresh: Boolean) {
        mRefresh?.let {
            it.setEnableRefresh(isRefresh)
        }
    }

    protected fun enableLoadMore(isLoadMore: Boolean) {
        mRefresh?.let {
            it.setEnableLoadMore(isLoadMore)
        }
    }

    protected fun enableRefreshAndLoadMore(enable: Boolean) {
        enableRefresh(enable)
        enableLoadMore(enable)
    }


    /**
     * 初始化刷新监听
     */
    private fun initRefreshListener() {
        mRefresh?.let {
            it.setOnRefreshListener {
                //pageIndex = 1
                isRefresh = true
                getRefreshData()
            }
            it.setOnLoadMoreListener {
                //pageIndex++
                isRefresh = false
                getLoadMoreData()
            }
        }
    }

    private fun initRefreshListener(refreshDate: () -> Unit, loadMoreData: () -> Unit) {
        mRefresh?.let {
            it.setOnRefreshListener {
                pageIndex = 1
                isRefresh = true
                refreshDate()
            }
            it.setOnLoadMoreListener {
                pageIndex++
                isRefresh = false
                loadMoreData()
            }
        }
    }

    /**
     * 下来刷新
     */
    abstract fun getRefreshData()

    /**
     * 上拉加载
     */
    abstract fun getLoadMoreData()

}