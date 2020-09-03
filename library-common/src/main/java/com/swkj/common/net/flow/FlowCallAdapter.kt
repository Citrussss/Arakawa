package com.sw.common.net.flow

import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * author pisa
 * date  2020/4/29
 * version 1.0
 * effect :kotlin Flow适配器构造工厂
 */
class FlowCallAdapter<R>(val responseType: Type, val isBody: Boolean) : CallAdapter<R, Any> {
    override fun adapt(call: Call<R>): Any {
        val flowSource = CallEnqueueFlow<R>(call)
        return when {
            isBody -> flowSource.map { it.body() }
            else -> flowSource
        }
    }

    override fun responseType(): Type {
        return responseType
    }
}