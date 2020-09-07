package com.sw.common.net.flow

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.sw.common.net.ApiException
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * author pisa
 * date  2020/4/29
 * version 1.0
 * effect :异步队列
 */
class CallEnqueueFlow<T>(val originalCall: Call<T>) : Flow<Response<T>> {

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<Response<T>>) {
        val call: Call<T> = originalCall.clone()
        val callBack: Response<T> = suspendCancellableCoroutine { continuation ->
            //在收到取消信息的时候，结束请求
            continuation.invokeOnCancellation {
                call.cancel()
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    when (t) {
                        is SocketException -> {
                            continuation.resumeWithException(ApiException(10057, "服务器连接超时"))
                        }
                        else -> continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val code = response.code()
                    when {
                        code == 200 -> continuation.resume(response)
                        code == 502 -> continuation.resumeWithException(
                            ApiException(502, "服务器繁忙，请稍后再试")
                        )
                        else -> {
                            try {
                                val infoEntity: JsonObject =
                                    GsonUtils.fromJson(response.errorBody()?.string(), JsonObject::class.java)
                                continuation.resumeWithException(ApiException(infoEntity.get("code").asInt,infoEntity.get("message").asString))
                            }catch (e:Exception){
                                continuation.resumeWithException(HttpException(response))
                            }
                        }
                    }
                }
            })
        }
        collector.emit(callBack)
    }
}