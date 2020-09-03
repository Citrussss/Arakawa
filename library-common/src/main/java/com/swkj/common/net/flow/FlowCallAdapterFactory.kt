package com.sw.common.net.flow

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.Result
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * author pisa
 * date  2020/4/29
 * version 1.0
 * effect :kotlin Flow适配器构造工厂
 */
class FlowCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType != Flow::class.java) {
            return null
        }
        var isResult = false
        var isBody = false
        var responseType: Type
        if (returnType !is ParameterizedType) {
            val name = "Flow"
            throw IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>")
        }

        val observableType = getParameterUpperBound(0, returnType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType == Response::class.java) {
            check(observableType is ParameterizedType) {
                ("Response must be parameterized"
                        + " as Response<Foo> or Response<? extends Foo>")
            }
            responseType = getParameterUpperBound(0, observableType)
        } else if (rawObservableType == Result::class.java) {
            check(observableType is ParameterizedType) {
                ("Result must be parameterized"
                        + " as Result<Foo> or Result<? extends Foo>")
            }
            responseType = getParameterUpperBound(0, observableType)
            isResult = true
        } else {
            responseType = observableType
            isBody = true
        }
        return FlowCallAdapter<Any?>(responseType,isBody)
    }

    companion object {
        fun create(): FlowCallAdapterFactory {
            return FlowCallAdapterFactory()
        }
    }
}