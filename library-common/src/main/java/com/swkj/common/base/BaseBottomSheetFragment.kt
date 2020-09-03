package com.swkj.common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.swkj.common.BuildConfig
import com.swkj.common.R
import java.lang.reflect.ParameterizedType

/**
 * author pisa
 * date  2020/2/26
 * version 1.0
 * effect :底部弹窗
 */
open class BaseBottomSheetFragment<Binding : ViewDataBinding?> : BottomSheetDialogFragment() {
    protected var binding: Binding? = null

    protected val mBottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback by lazy{
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //禁止拖拽，
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    //设置为收缩状态
                    val parent = binding!!.root.parent as View
                    val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                    val behavior = params.behavior
                    val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
                    bottomSheetBehavior?.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        }
    }

    /**
     * 反射获取视图绑定对象
     *
     * @return
     */
    protected fun createViewBind(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Binding? {
        return this.let {
            try {
                var clazz: Class<in Any>? = this.javaClass
                while (clazz != null) {
                    val genericSuperclass = clazz.genericSuperclass
                    if (genericSuperclass is ParameterizedType) {
                        for (type in genericSuperclass.actualTypeArguments) {
                            try {
                                if (binding != null) {
                                    break
                                }
                                val clazz = type as Class<Binding>
                                val inflate = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType)
                                binding = inflate.invoke(null, inflater, container, false) as Binding
                            } catch (e: Exception) {
                                if (BuildConfig.DEBUG) {
                                    e.printStackTrace()
                                }
                            } finally {
                                if (binding != null) {
                                    return@let binding
                                }
                            }
                        }
                    }
                    clazz = clazz.superclass
                }
                return null
            } catch (e: Exception) {
                return null
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = createViewBind(inflater, container, savedInstanceState).apply {
                initView(this)
            }
        } else {
            val parent = binding?.root?.parent as ViewGroup
            parent.removeView(binding?.root)
        }
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.background = ColorDrawable(Color.TRANSPARENT)
    }

    open fun initView(binding: Binding?) {}
    open fun initData() {}
    open fun initListener(){}
}