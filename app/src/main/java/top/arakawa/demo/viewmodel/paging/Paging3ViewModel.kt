package top.arakawa.demo.viewmodel.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.swkj.common.base.BaseModel
import com.swkj.common.base.paging.BasePagingDataSource3
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import top.arakawa.demo.net.api.WanAndroidApi
import top.arakawa.demo.net.bean.Article
import top.arakawa.demo.net.bean.Page
import top.arakawa.demo.net.bean.WanAndroidBaseBean
import top.arakawa.ktor.Http

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
class Paging3ViewModel : BaseModel() {
    private val api by lazy { WanAndroidApi.api }

    fun getArticle(): LiveData<PagingData<Article>> {
//        viewModelScope.launch {
//
//        }
        return Pager(PagingConfig(pageSize = 20)) {
            BasePagingDataSource3 { page, size ->
//                LogUtils.d("pisa","开始加载数据")
//                val string =
//                    Http.httpClient.get<String>("article/list/${page}/json") {
//                        contentType(ContentType.Application.Json)
//                    }
//                LogUtils.d("pisa","加载数据完毕${GsonUtils.toJson(string)}")
//               return@BasePagingDataSource3 Http.httpClient.get<WanAndroidBaseBean<Page<Article>>>("article/list/${page}/json") {
//                    contentType(ContentType.Application.Json)
//                }.data.datas
                Http.httpClient.get<WanAndroidBaseBean<Page<Article>>>("article/list/${page}/json") {
//                    contentType(ContentType.Application.Json)
                }.data.datas
            }
        }.flow.asLiveData()
    }
}