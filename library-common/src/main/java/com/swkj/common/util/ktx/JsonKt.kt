package com.swkj.common.util.ktx

import com.blankj.utilcode.util.GsonUtils

/**
 * @author pisa
 * @date  2020/6/29
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
fun Any?.toJson(): String = GsonUtils.toJson(this)
