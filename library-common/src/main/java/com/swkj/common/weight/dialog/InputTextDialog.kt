package com.sw.common.weight.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.util.Consumer
import androidx.fragment.app.DialogFragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.swkj.common.R
import com.swkj.common.base.BaseBottomSheetFragment
import com.swkj.common.constant.Constant
import com.swkj.common.databinding.PopInputTextBinding
import com.swkj.common.listener.OnSafeClickListener
import com.swkj.common.util.EditViewUtils
import com.swkj.common.util.SoftInputUtil

/**
 * author pisa
 * date  2020/3/19
 * version 1.0
 * effect :文字输入框
 */
open class InputTextDialog : BaseBottomSheetFragment<PopInputTextBinding?>() {

    private var inputType = InputType.TYPE_CLASS_TEXT
    private var hint = "请输入。。。"
    /**
     * 发送文本
     */
    private var onSendClick: Consumer<EditText>? = null

    //    这是设置禁止滚动的办法
    //    private BottomSheetBehavior<View> mBottomSheetBehavior;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置控件不随光标移动，防止遮盖
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetEdit)
    }

    override fun initView(binding: PopInputTextBinding?) {
        super.initView(binding)
        binding?.etInput?.apply {
            setOnEditorActionListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    send()
                    true
                } else false
            }
            EditViewUtils.setEnterAction(this) {
                send()
            }
        }

//        binding?.tvSend?.setOnClickListener(onSafeClickListener)
    }

    fun setHint(text: String) {
        hint = text
    }

    private val onSafeClickListener: OnSafeClickListener = object : OnSafeClickListener() {
        override fun onSafeClick(v: View) {
            val id = v.id
            if (id == R.id.tv_send) {
                send()
            }
        }
    }

    /**
     * 发送
     */
    private fun send() {
        if (onSendClick != null) {
            try {
                onSendClick?.accept(binding?.etInput)
                binding?.etInput?.setText("")
                dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setInputType()
        BarUtils.setStatusBarColor(dialog?.window!!, Color.parseColor("#00000000"))
        binding?.tvSend?.postDelayed({
            binding?.etInput?.requestFocus()
            SoftInputUtil.toggleInputMethod(binding?.etInput)
        }, 333)
        binding?.etInput?.hint
    }

    fun setOnSendClick(onSendClick: Consumer<EditText>?) {
        this.onSendClick = onSendClick
    }

    override fun onPause() {
        super.onPause()
        ActivityUtils.getTopActivity().window.decorView.postDelayed({
            SoftInputUtil.hideInputMethod(ActivityUtils.getTopActivity().window.decorView)
        }, 333)
    }

    public fun setInputType(inputType: Int = InputType.TYPE_CLASS_TEXT) {
        this@InputTextDialog.inputType = inputType
        binding?.etInput?.inputType = inputType
    }

    companion object {
        fun newInstance(hint: String? = null): InputTextDialog {
            val args = Bundle().apply {
                hint?.let { this.putString(Constant.KEY_HINT, it) }
            }
            val fragment = InputTextDialog()
            fragment.arguments = args
            return fragment
        }
    }
}