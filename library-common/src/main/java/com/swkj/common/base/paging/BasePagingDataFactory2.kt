package com.sw.common.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import java.util.concurrent.Executor

/**
 * 分页数据源工厂
 */
class BasePagingDataFactory2<T:Any>(val api: suspend (page: Long, size: Int) -> List<T>?) : DataSource.Factory<Long, T>() {
    val sourceLiveData = MutableLiveData<BasePagingDataSource2<T>>()
    override fun create(): DataSource<Long, T> {
        val source = BasePagingDataSource2<T>(api, Executor {})
        sourceLiveData.postValue(source)
        return source
    }
}