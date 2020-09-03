package com.sw.common.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.blankj.utilcode.util.LogUtils
import com.swkj.common.constant.NetConstant
import com.swkj.common.net.NetworkState
import com.swkj.common.result.Results
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor

/**
 * author pisa
 * date  2020/4/3
 * version 1.0
 * effect ：分页数据源
 */
class BasePagingDataSource<T:Any>(val api: suspend ((page: Long, size: Int) -> Results<List<T>>), private val retryExecutor: Executor) : PageKeyedDataSource<Long, T>() {

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    //    private var api: ((page: Long, size: Int) -> List<LiveBean>)?=null
    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, T>) {
        LogUtils.d("loadInitial==>>列表")
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        val preProcessing = runBlocking {
            val preProcessing = api(NetConstant.PAGINATION, params.requestedLoadSize)
            when (preProcessing) {
                is Results.Success -> {
                    retry = null
                    if(preProcessing.data?.isNotEmpty()==false){
                        networkState.postValue(NetworkState.EMPTY)
                        initialLoad.postValue(NetworkState.EMPTY)
                    }else{
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                    }
                    (preProcessing.data
                            ?: ArrayList()).let { callback.onResult(it, null, NetConstant.PAGINATION + 1) }
                }
                is Results.Error -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    val error = NetworkState.error(preProcessing.exception.message
                            ?: "unknown error")
                    networkState.postValue(error)
                    initialLoad.postValue(error)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {
        LogUtils.d("loadAfter==>>列表")
        networkState.postValue(NetworkState.LOADING)
        val preProcessing = runBlocking {
            val preProcessing = api(params.key, params.requestedLoadSize)
            when (preProcessing) {
                is Results.Success -> {
                    networkState.postValue(NetworkState.LOADED)
                    (preProcessing.data
                            ?: ArrayList()).let { callback.onResult(it, params.key + 1) }
                }
                is Results.Error -> {
                    val error = NetworkState.error(preProcessing.exception.message
                            ?: "unknown error")
                    networkState.postValue(error)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {
        LogUtils.d("loadBefore==>>列表")
        callback.onResult(ArrayList(), null)
    }
}