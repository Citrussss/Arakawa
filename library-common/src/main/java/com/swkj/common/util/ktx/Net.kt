package com.swkj.common.util.ktx

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sw.common.net.RetrofitClient
import kotlin.reflect.KClass

/**
 * @author pisa
 * @date  2020/6/18
 * @version 1.0
 * @effect : wubba lubba dub dub 创建api
 */
inline fun <reified API : Any> Any.apis(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<API> {
    return ApiLazy<API>(API::class)
}

class ApiLazy<API : Any>(
    private val apiClass: KClass<API>
) : Lazy<API> {
    private var cached: API? = null
    override val value: API
        get() {
            val api = cached
            return if (api == null) {
                return RetrofitClient.instance.getApi(apiClass.java).apply { cached = this }
            } else {
                return api
            }
        }

    override fun isInitialized(): Boolean = cached != null
}