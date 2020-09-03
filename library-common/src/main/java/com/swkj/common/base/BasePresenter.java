package com.swkj.common.base;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import com.sw.common.net.RetrofitClient;
import com.swkj.common.constant.NetConstant;
import com.swkj.common.net.ApiService;

/**
 * Created by Administrator on 2018/12/16.
 */

public class BasePresenter<V extends BaseView> extends ViewModel {
    protected V mView;


    protected ApiService mModel;

    /**
     * 绑定view接口
     *
     * @param v view
     */
    public void attachView(V v) {
        this.mView = v;
        mModel = RetrofitClient.Companion.getInstance().getApi(ApiService.class,RetrofitClient.Companion.getDefaultHost());
    }


    /**
     * 解绑view接口
     */
    void detachView() {
        if (this.mView != null) {
            this.mView = null;
        }
        if (mModel != null) {
            mModel = null;
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
