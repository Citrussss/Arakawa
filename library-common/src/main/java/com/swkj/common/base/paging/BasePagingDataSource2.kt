package com.sw.common.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.blankj.utilcode.util.LogUtils
import com.swkj.common.constant.NetConstant
import com.swkj.common.net.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

/**
 * author pisa
 * date  2020/4/3
 * version 1.0
 * effect ：分页数据源
 */
class BasePagingDataSource2<T:Any>(
    val api: suspend (page: Long, size: Int) -> List<T>?,
    private val retryExecutor: Executor
) : PageKeyedDataSource<Long, T>() {

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

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, T>
    ) {
        LogUtils.d("loadInitial==>>列表")
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = api(NetConstant.PAGINATION, params.requestedLoadSize)
                LogUtils.d("page:${NetConstant.PAGINATION},size:${params.requestedLoadSize}：${result}")
                retry = null
                if (result?.isEmpty() != false) {
                    networkState.postValue(NetworkState.EMPTY)
                    initialLoad.postValue(NetworkState.EMPTY)
                } else {
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                }
                callback.onResult(result ?: ArrayList(), null, NetConstant.PAGINATION + 1)
//                networkState.postValue(NetworkState.LOADED)
//                initialLoad.postValue(NetworkState.LOADED)
            } catch (e: Exception) {
                retry = {
                    loadInitial(params, callback)
                }
                val error = NetworkState.error(e.message)
                networkState.postValue(error)
                initialLoad.postValue(error)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {
        LogUtils.d("loadAfter==>>列表")
        networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = api(params.key, params.requestedLoadSize)
                LogUtils.d("第${params.key}页加载 ,size:${params.requestedLoadSize}：${result}")
                retry = null
                if (result?.isEmpty() != false) {
                    networkState.postValue(NetworkState.EMPTY)
                } else {
                    networkState.postValue(NetworkState.LOADED)
                }
                callback.onResult(result ?: ArrayList(), params.key + 1)
            } catch (e: Exception) {
                retry = {
                    loadAfter(params, callback)
                }
                val error = NetworkState.error(e.message)
                networkState.postValue(error)
                initialLoad.postValue(error)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {
        LogUtils.d("loadBefore==>>列表")
        callback.onResult(ArrayList(), null)
    }
}