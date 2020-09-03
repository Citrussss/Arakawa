package com.swkj.common.util

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

/**
 * author pisa
 * date  2019/12/13
 * version 1.0
 * effect :
 */
object EditViewUtils {
    /**
     * 软键盘的回车键的回调
     *
     * @param editText 文本框
     * @param onNext   输入中
     * @param onFinish 输入结束
     */
    fun setEnterAction(
        editText: EditText,
        onNext: Function1<String, Unit>?,
        onFinish: Function1<TextView, Unit>?
    ) {
        editText.setOnEditorActionListener { v: TextView, actionId: Int, keyEvent: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.keyCode && KeyEvent.ACTION_DOWN == keyEvent.action) {
                if (onFinish != null) {
                    try {
                        onFinish.invoke(v)
                        return@setOnEditorActionListener true
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            false
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (onNext != null) {
                    try {
                        onNext.invoke(s.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun setEnterAction(editText: EditText, onFinish: Function1<TextView, Unit>) {
        setEnterAction(editText, { }, onFinish)
    }
}