package com.swkj.common.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.swkj.common.R;

/**
 * package ：com.bs.blaufuchslive.ui.activity
 * author : kele
 * date : 2020/2/17 11:14
 * description :下拉刷新和加载更多Fragment基类
 */
public abstract class BaseRefreshFragment<T extends BasePresenter<?>> extends BaseFragment<T>
        implements OnRefreshLoadMoreListener {

    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRv;

    @Override
    protected void initView() {
        super.initView();
        mRefreshLayout = mRootView.findViewById(R.id.refresh_layout);
        mRv = mRootView.findViewById(R.id.rv);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected Object getRootView() {
        return R.layout.common_fragment_base_refresh_load_more;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        onLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        onRefresh();
    }

    /**
     * 刷新
     */
    protected abstract void onRefresh();

    /**
     * 加载更多
     */
    protected abstract void onLoadMore();


    /**
     * 数据为空
     */
    /*protected void onDataEmpty(){
        mLlEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
    }
    *//**
     * 刷新后数据不为空
     * *//*
    protected void onDataShow(){
        if (mRefreshLayout.getVisibility() == View.GONE) {
            mLlEmpty.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
    }*/
}
