package top.arakawa.demo.app

import android.app.Application
import com.sw.common.net.RetrofitClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * @author pisa
 * @date  2020/9/2
 * @version 1.0
 * @effect : wubba lubba dub dub
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.instance.addInterceptors(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }
}