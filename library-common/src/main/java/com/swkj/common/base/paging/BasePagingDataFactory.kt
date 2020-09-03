package com.sw.common.base.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.swkj.common.result.Results
import java.util.concurrent.Executor

/**
 * 分页数据源工厂
 */
class BasePagingDataFactory<T:Any>(val api: suspend ((page: Long, size: Int) -> Results<List<T>>)) : DataSource.Factory<Long, T>() {
    val sourceLiveData = MutableLiveData<BasePagingDataSource<T>>()
    override fun create(): DataSource<Long, T> {
        val source = BasePagingDataSource<T>(api, Executor { })
        sourceLiveData.postValue(source)
        return source
    }
}