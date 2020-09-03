package com.swkj.common.util.ktx

import com.blankj.utilcode.util.SizeUtils

/**
 * @author pisa
 * @date  2020/5/17
 * @version 1.0
 * @effect : BlaufuchsLive
 */
val Float.dp get() = SizeUtils.dp2px(this)
val Int.dp get() = this.toFloat().dp
val Double.dp get() = this.toFloat().dp
