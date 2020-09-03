package com.sw.common.net

/**
 * author pisa
 * date  2020/3/31
 * version 1.0
 * effect :接口异常
 */
class ApiException(val code: Int, val msg: String?) : RuntimeException(msg) {
    override fun toString(): String {
        return "code:$code msg:${super.toString()}"
    }
}