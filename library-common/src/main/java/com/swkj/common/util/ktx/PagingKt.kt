package com.swkj.common.util.ktx

import androidx.lifecycle.switchMap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sw.common.base.paging.BasePagingDataFactory2
import com.sw.common.base.paging.Listing

/**
 * author pisa
 * date  2020/5/20
 * version 1.0
 * effect :
 */
object PagingKt {
    /**
     * 简化过程
     */
/*    fun <T : Any> paging(api: suspend (page: Long, size: Int) -> List<T>?): Listing<T> {
//        val sourceFactory = BasePagingDataFactory2<T>(api = { page, size ->
//            api.invoke(page, size)
//        })
//        Pager(
//            PagingConfig(pageSize = 10, initialLoadSize = 10),
//            pagingSourceFactory = sourceFactory::create()
//        )
//        val livePagedList = sourceFactory(
//                Config(pageSize = 10, initialLoadSizeHint = 10))
        val refreshState = sourceFactory.sourceLiveData.switchMap { it.initialLoad }
        return Listing(
            pagedList = livePagedList,
            networkState = sourceFactory.sourceLiveData.switchMap {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }*/
}