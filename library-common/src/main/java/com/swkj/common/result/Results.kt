package com.swkj.common.result

/**
 * author pisa
 * date  2020/3/31
 * version 1.0
 * effect :请求状态
 */
sealed class Results<out T : Any> {
    data class Success<out T : Any>(val data: T?, val msg: String? = null) : Results<T>()
    data class Error(val exception: Throwable) : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}