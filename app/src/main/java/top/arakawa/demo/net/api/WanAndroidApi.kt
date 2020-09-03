package top.arakawa.demo.net.api

import com.sw.common.net.RetrofitClient
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import top.arakawa.demo.net.bean.Article
import top.arakawa.demo.net.bean.Page
import top.arakawa.demo.net.bean.WanAndroidBaseBean

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
interface WanAndroidApi {
    //    https://www.wanandroid.com/article/list/0/json
//
//    方法：GET
//    参数：页码，拼接在连接中，从0开始。
    @GET("article/list/{page}/json")
    fun articleList(@Path("page") page: Long): Flow<WanAndroidBaseBean<Page<Article>>>

    companion object {

        const val BASE_API: String = "https://www.wanandroid.com/"

        val api by lazy { RetrofitClient.instance.getApi(WanAndroidApi::class.java, BASE_API) }
    }
}