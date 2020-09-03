package com.swkj.common.base.paging

import androidx.paging.PagingSource

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub Paging3模型
 */
class BasePagingDataSource3<T : Any>(
    val api: suspend (page: Long, size: Int) -> List<T>
) : PagingSource<Long, T>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, T> {
        return try{
            val page = params.key ?: 0
            //获取网络数据
            val result = api.invoke(page, params.loadSize)
            LoadResult.Page<Long, T>(
                //需要加载的数据
                data = result,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = if (page > 0) page - 1 else null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = if (result.isNotEmpty()) page + 1 else null
            )
        }catch (e:Exception){
            LoadResult.Error<Long,T>(e)
        }
    }
}