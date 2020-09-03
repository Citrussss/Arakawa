package top.arakawa.demo.viewmodel.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.swkj.common.base.BaseModel
import com.swkj.common.base.paging.BasePagingDataSource3
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import top.arakawa.demo.net.api.WanAndroidApi
import top.arakawa.demo.net.bean.Article

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
class Paging3ViewModel : BaseModel() {
    private val api by lazy { WanAndroidApi.api }

    fun getArticle(): LiveData<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 20)) {
            BasePagingDataSource3 { page, size ->
                api.articleList(page = page).map { it.data.datas }.single()
            }
        }.flow.asLiveData()
    }
}