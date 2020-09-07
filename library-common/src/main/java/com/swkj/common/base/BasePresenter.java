package com.swkj.common.base;

import androidx.lifecycle.ViewModel;

/**
 * Created by Administrator on 2018/12/16.
 */

public class BasePresenter<V extends BaseView> extends ViewModel {
    protected V mView;



    /**
     * 绑定view接口
     *
     * @param v view
     */
    public void attachView(V v) {
        this.mView = v;
    }


    /**
     * 解绑view接口
     */
    void detachView() {
        if (this.mView != null) {
            this.mView = null;
        }
    }

    /**
     * 是否绑定view接口
     *
     * @return
     */
    public boolean isAttachView() {
        return this.mView != null;
    }

}
