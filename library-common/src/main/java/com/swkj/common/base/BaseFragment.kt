package com.swkj.common.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.sw.common.net.ApiException
import com.swkj.common.R
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by l on 2018/9/14.
 */
abstract class BaseFragment<T : BasePresenter<*>> :
    Fragment(), BaseView {

    protected lateinit var mRootView: View
    protected val mContext: Context by lazy { requireContext() }
    protected lateinit var mPresenter: T
    protected var isInit = false
    protected fun isPersonInitialized(): Boolean = ::mPresenter.isInitialized

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = getRootView()
        if (rootView is Int) {
            mRootView = LayoutInflater.from(mContext).inflate(rootView, null)
        } else if (rootView is View) {
            mRootView = rootView
        }
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isInit) {
            initView()
            isInit = true
        }
        initData()
    }

    /**
     * 获取view
     *
     * @return
     */
    protected abstract fun getRootView(): Any

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * 初始化view
     */
    protected open fun initView() {}

    protected open fun initListener() {}

    override fun onDestroyView() {
        super.onDestroyView()
        // 解绑view
        if (this::mPresenter.isInitialized) {
            mPresenter.detachView()
        }
    }

    /**
     * 启动Activity
     *
     * @param clazz
     */
    protected fun startActivity(clazz: Class<Activity?>?) {
        val intent = Intent(mContext, clazz)
        startActivity(intent)
    }

    override fun onServerError(throwable: Throwable) {
        if (throwable is ApiException) {
            ToastUtils.showShort(throwable.msg)
        } else {
            CrashReport.postCatchedException(throwable)
            ToastUtils.showShort(R.string.common_server_error)
        }
        LogUtils.dTag("okhttp",throwable)
    }

    protected fun showToast(msg: String?) {
        ToastUtils.showShort(msg)
    }

    protected fun showToast(id: Int) {
        ToastUtils.showShort(id)
    }

    override fun onShowToast(message: String?) {
        showToast(message)
    }


}