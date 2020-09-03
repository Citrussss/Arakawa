package com.swkj.common.base

import android.R.id
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.tabs.TabLayout
import com.sw.common.net.ApiException
import com.swkj.common.R
import com.swkj.common.receiver.NetworkReceiver
import com.tencent.bugly.crashreport.CrashReport
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CustomAdapt

/**
 * author  :  kele
 * date    :  2018/12/18 10:15
 * desc    :  Activity的基类
 * version :  v1.0
 */
abstract class BaseActivity<T : BasePresenter<*>> :
    AppCompatActivity(), BaseView, CustomAdapt {

    protected val mContext: Context by lazy { this.applicationContext }
    protected lateinit var mPresenter: T
    private var receiver: NetworkReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getResLayout())
        setVerticalScreen()
        startNetWordReceiver()
        initView()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    /**
     * 获取布局文件
     *
     * @return 返回布局文件资源id
     */
    protected abstract fun getResLayout(): Int

    /**
     * 初始化view
     */
    protected open fun initView() {
        setLightMode()
    }

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    protected open fun initListener() {}

    override fun onDestroy() {
        super.onDestroy()
        // 解绑view
        if (this::mPresenter.isInitialized) {
            mPresenter.detachView()
        }

        //解绑receiver
        if (receiver != null) {
            unregisterReceiver(receiver)
        }
    }

    protected fun setTranslucentStatus() {
        if (VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.BLACK
        }
    }

    protected fun setLightMode() {
        if (VERSION.SDK_INT >= VERSION_CODES.M) { // 设置状态栏字体黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    /**
     * 设置当前屏幕方向为竖屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private fun setVerticalScreen() {
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    protected fun showToast(msg: String?) {
        ToastUtils.showShort(msg)
    }

    protected fun showToast(id: Int) {
        ToastUtils.showShort(id)
    }

    protected fun startActivity(clazz: Class<*>?) {
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
        LogUtils.eTag("throwable", throwable)
    }


    /**
     * 对tabLayout的指示器的长度的处理
     *
     * @param tabLayout
     * @param marginOffset
     */
    fun reduceMarginsInTabs(tabLayout: TabLayout, marginOffset: Int) {
        val tabStrip = tabLayout.getChildAt(0)
        if (tabStrip is ViewGroup) {
            for (i in 0 until tabStrip.childCount) {
                val tabView = tabStrip.getChildAt(i)
                if (tabView.layoutParams is MarginLayoutParams) {
                    (tabView.layoutParams as MarginLayoutParams).leftMargin = marginOffset
                    (tabView.layoutParams as MarginLayoutParams).rightMargin =
                        marginOffset
                }
            }
            tabLayout.requestLayout()
        }
    }

    override fun onShowToast(message: String?) {
        showToast(message)
    }

    private fun startNetWordReceiver() { //判断当前版本是否大于7.0
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            receiver = NetworkReceiver()
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(receiver, filter)
        }
    }

    override fun isBaseOnWidth(): Boolean {
        return true
    }

    override fun getSizeInDp(): Float {
        return 375f
    }

    /**
     * @return 获取根布局
     */
    val contentView: View
        get() = (findViewById<View>(id.content) as ViewGroup).getChildAt(0)

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            AutoSize.autoConvertDensityOfGlobal(this)
        }
    }

    override fun getResources(): Resources {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources())) //如果没有自定义需求用这个方法
        }
        return super.getResources()
    }
//    override getResources():Resources {
//        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
//        AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources());//如果没有自定义需求用这个方法
//        AutoSizeCompat.autoConvertDensity((super.getResources(), 667, false);//如果有自定义需求就用这个方法
//        return super.getResources();
//    }
}