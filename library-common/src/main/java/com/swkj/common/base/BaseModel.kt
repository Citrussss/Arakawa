package com.swkj.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils
import com.sw.common.net.ApiException
import com.swkj.common.net.NetworkState

/**
 * author pisa
 * date  2020/5/15
 * version 1.0
 * effect :viewModel基类
 */
open class BaseModel : ViewModel() {
    public val networkState by lazy { MutableLiveData<NetworkState>() }
    public fun onError(t: Throwable) {
        if (t is ApiException) {
            networkState.postValue(NetworkState.error(t.message))
        }
        LogUtils.dTag("pisa", t)
    }

    public fun onError(msg: String?) {
        onError(ApiException(300, msg?:""))
    }
}