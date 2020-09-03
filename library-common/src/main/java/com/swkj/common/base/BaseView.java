package com.swkj.common.base;


/**
 * Created by Administrator on 2018/12/16.
 */

public interface BaseView {
    /**
     * 显示加载加载框
     */
    void onShowLoading();

    /**
     * 加载框消失
     */
    void onHideLoading();

    /**
     * 服务器异常
     */
    void onServerError(Throwable throwable);

    void onShowToast(String message);
}
