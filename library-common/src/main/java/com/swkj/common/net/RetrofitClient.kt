package com.sw.common.net
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.blankj.utilcode.util.LogUtils
import com.sw.common.net.RetrofitClient
import com.sw.common.net.flow.FlowCallAdapterFactory.Companion.create
import com.swkj.common.constant.NetConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * 设计应该具有可扩展性
 */
class RetrofitClient private constructor() {
    /**
     * 所有接口对象存放容器
     */
    private val apiMaps = HashMap<String, Any?>()

    private val interceptors = ArrayList<Interceptor>()

    private val netInterceptors = ArrayList<Interceptor>()


    fun <T> getApi(tClass: Class<T>, bashHost: String = defaultHost): T {
        var o: T? = null
        val key = tClass.name + bashHost
        try {
            o = apiMaps[key] as T?
        } catch (e: Exception) {
            LogUtils.e(e)
        }
        if (o == null) {
            synchronized(RetrofitClient::class.java) {
                try {
                    o = apiMaps[key] as T?
                } catch (e: Exception) {
                    LogUtils.e(e)
                }
                if (o == null) {
                    o = buildApi(tClass, bashHost)
                    apiMaps[key] = o
                }
            }
        }
        return o!!
    }

    private fun <T> buildApi(tClass: Class<T>, bashHost: String): T? {
        LogUtils.d("buildApi")
        //初始化一个client,不然retrofit会自己默认添加一个
        var t: T? = null
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .apply {
                this.cookieJar(CookieJarImpl.instance)
                this@RetrofitClient.interceptors.forEach { this.addInterceptor(it) }
                this@RetrofitClient.netInterceptors.forEach { this.addNetworkInterceptor(it) }
//            .addInterceptor(headerInterceptor)
//                this@RetrofitClient.addNetInterceptors(interceptor)
//            .addInterceptor(TokenFailInterceptor())
//            .addInterceptor(DataEmptyInterceptor())
            }
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            //设置网络请求的Url地址
            .baseUrl(bashHost)
            //设置数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            //设置网络请求适配器，使其支持RxJava与RxAndroid
            .addCallAdapterFactory(create())
            .build()
        //创建—— 网络请求接口—— 实例
        t = retrofit.create(tClass)
        return t
    }

    companion object {
        var defaultHost: String = NetConstant.BASE_URL
        val instance: RetrofitClient by lazy(RetrofitClient::class.java) { RetrofitClient() }
    }

    fun addInterceptors(vararg interceptors: Interceptor) {
        LogUtils.d("addInterceptors start ${interceptors.size}")
        interceptors.forEach {
            if (!this.interceptors.contains(it)) {
                LogUtils.d(it)
                this.interceptors.add(it)
            }
        }
        LogUtils.d("addInterceptors end ${this.interceptors.size}")
        clearApiContainer()
    }

    fun addNetInterceptors(vararg interceptors: Interceptor) {
        LogUtils.d("addNetInterceptors start ${interceptors.size}")
        interceptors.forEach {
            if (!this.netInterceptors.contains(it)) {
//                LogUtils.d(it)
                this.netInterceptors.add(it)
            }
        }
        LogUtils.d("addNetInterceptors end ${netInterceptors.size}")
        clearApiContainer()
    }

    fun clearApiContainer() {
        apiMaps.clear()
    }
}