package com.swkj.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swkj.common.R

/**
 * author : kele
 * date :  2020/3/26 16:55
 * description :
 */
open class BaseBottomFragment(private val rootView: Any) : BottomSheetDialogFragment() {
    protected val mRootView: View by lazy {
        val view = when (rootView) {
            is Int -> {
                LayoutInflater.from(context).inflate(rootView, null)
            }
            is View -> {
                rootView
            }
            else -> View(context)
        }
        return@lazy view
    }
    //    /**
//     * 获取view
//     *
//     * @return
//     */
//    abstract fun getRootView(): Any;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView.apply {
            if (mRootView.parent is ViewGroup) {
                (mRootView.parent as ViewGroup).removeView(this)
            } else {
                initView()
            }
        }
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initData()
    }


    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * 初始化view
     */
    protected open fun initView() {}

    fun onShowLoading() {}
    fun onHideLoading() {}
    override fun onDestroyView() {
        super.onDestroyView()
    }
}