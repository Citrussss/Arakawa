package com.swkj.common.util.ktx

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import java.util.concurrent.Executor

/**
 * author : kele
 * date :  2020/4/20 10:47
 * description :
 */
@SuppressLint("RestrictedApi")
fun <Key:Any, Value:Any> DataSource.Factory<Key, Value>.toLiveData(
        pageSize: Int,
        initialLoadKey: Key? = null,
        boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
        fetchExecutor: Executor = ArchTaskExecutor.getIOThreadExecutor()
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, Config(pageSize = pageSize, initialLoadSizeHint = pageSize))
            .setInitialLoadKey(initialLoadKey)
            .setBoundaryCallback(boundaryCallback)
            .setFetchExecutor(fetchExecutor)
            .build()
}