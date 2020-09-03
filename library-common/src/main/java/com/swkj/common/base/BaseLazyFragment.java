package com.swkj.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public abstract class BaseLazyFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected View mRootView;
    protected Context mContext;
    protected T mPresenter;
    /**
     * 页面是否可见
     */
    private boolean mIsVisibleToUser;
    /**
     * 页面是否创建完成
     */
    private boolean mIsViewCreate;
    /**
     * 是否已经加载过数据
     */
    private boolean mIsHasLoad;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        Object rootView = getRootView();
        //  创建RootView
        if (rootView instanceof Integer) {
            Integer resId = (Integer) rootView;
            mRootView = LayoutInflater.from(mContext).inflate(resId, null);
        } else if (rootView instanceof View) {
            mRootView = ((View) rootView);
        }
        initView();
        mIsViewCreate = true;
        //  进行加载数据
        if (getUserVisibleHint()) {
            onLazyLoad();
            mIsViewCreate = false;
        }
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //  进行加载数据
        if (isVisibleToUser && mIsViewCreate) {
            onLazyLoad();
            mIsViewCreate = false;
        }
    }

    /**
     * 进行加载数据
     */
    protected abstract void onLazyLoad();

    /**
     * 获取view
     *
     * @return
     */
    protected abstract Object getRootView();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化view
     */
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解绑view
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

//    /**
//     * 绑定生命周期 防止MVP内存泄漏
//     *
//     * @param <T>
//     * @return
//     */
//    @Override
//    public <T> AutoDisposeConverter<T> bindAutoDispose() {
//        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
//                .from(this, Lifecycle.Event.ON_DESTROY));
//    }
}
