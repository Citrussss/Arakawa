//package com.sw.common.net
//
//import com.blankj.utilcode.util.LogUtils
//import com.swkj.common.BuildConfig
//import com.swkj.common.constant.NetConstant
//import com.swkj.common.result.Results
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import kotlin.coroutines.resume
//import kotlin.coroutines.suspendCoroutine
//
///**
// * author pisa
// * date  2020/3/31
// * version 1.0
// * effect :
// */
//
///**
// * Response预处理
// */
//fun <T : Any> Response<BaseBean<T>>.preProcessing(): Results<T> {
//    return try {
//        return if (this.isSuccessful) {
//            return body()?.let {
//                return@let if (it.code == NetConstant.SUCCESS_CODE) {
//                    Results.Success(it.data, it.message)
//                } else {//业务逻辑上的错误处理
//                    Results.Error(ApiException(it.code, it.message))
//                }
//            } ?: Results.Error(Exception("接口异常"))
//        } else if (this.code() == 500) {            //这里实际上应该对各种异常做处理，如网络异常 数据库异常等
////            val json = this.errorBody()?.string()
////            val error = GsonUtils.fromJson<ServiceErrorBean>(json, ServiceErrorBean::class.java)
////            Results.Error(ApiException(error.status, "服务器异常：path:${error.path}\n message:${error.message}\n"))
//            if (BuildConfig.DEBUG) {
//                Results.Error(ApiException(500, "服务器异常\n${this.errorBody()?.string()}"))
//            } else {
//                Results.Error(ApiException(500, "服务器异常"))
//            }
//        } else {
//            Results.Error(Exception(this.errorBody()?.string()))
//        }
//    } catch (e: Exception) {
//        LogUtils.d(e)
//        Results.Error(e)
//    }
//}
//
//suspend fun <T : Any> Call<BaseBean<T>>.preProcessing(): Results<T> = suspendCoroutine { uCont ->
//    this.enqueue(object : Callback<BaseBean<T>> {
//        override fun onResponse(call: Call<BaseBean<T>>, response: Response<BaseBean<T>>) {
//            uCont.resume(response.preProcessing())
//        }
//
//        override fun onFailure(call: Call<BaseBean<T>>, t: Throwable) {
//            uCont.resume(Results.Error(t))
//        }
//    })
////    this.enqueue()
//
//}
//
///**
// * 返回原始数据
// */
//suspend fun <T : Any> Call<BaseBean<T>>.preProcessingUnMap(): Results<BaseBean<T>> =
//    suspendCoroutine { uCont ->
//        this.enqueue(object : Callback<BaseBean<T>> {
//            override fun onResponse(call: Call<BaseBean<T>>, response: Response<BaseBean<T>>) {
//                val preProcessing = response.preProcessing()
//                when (preProcessing) {
//                    is Results.Success -> {
//                        uCont.resume(Results.Success(response.body()))
//                    }
//                    is Results.Error -> {
//                        uCont.resume(preProcessing)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BaseBean<T>>, t: Throwable) {
//                uCont.resume(Results.Error(t))
//            }
//        })
////    this.enqueue()
//
//    }
//
///**
// * 返回原始数据
// */
//suspend fun <T : Any> Call<BaseBean<T?>>.preProcessingUnCheack(): Results<BaseBean<T?>> =
//    suspendCoroutine { uCont ->
//        this.enqueue(object : Callback<BaseBean<T?>> {
//            override fun onResponse(call: Call<BaseBean<T?>>, response: Response<BaseBean<T?>>) {
//                val preProcessing = (response as Response<BaseBean<T>>).preProcessing()
//                when (preProcessing) {
//                    is Results.Success -> {
//                        uCont.resume(Results.Success(response.body()!! as BaseBean<T?>))
//                    }
//                    is Results.Error -> {
//                        uCont.resume(preProcessing)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<BaseBean<T?>>, t: Throwable) {
//                uCont.resume(Results.Error(t))
//            }
//        })
////    this.enqueue()
//
//    }