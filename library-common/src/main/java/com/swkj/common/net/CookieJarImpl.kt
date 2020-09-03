package com.sw.common.net

import com.blankj.utilcode.util.LogUtils
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author pisa
 * @date  2020/6/8
 * @version 1.0
 * @effect : BlaufuchsLive
 */
class CookieJarImpl : CookieJar {
    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = cookieStore[url.host]
        return cookies ?: ArrayList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    companion object {
        val instance: CookieJar by lazy { CookieJarImpl() }
    }
}