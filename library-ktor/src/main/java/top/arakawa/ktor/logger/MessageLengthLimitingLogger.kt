package top.arakawa.ktor.logger

import android.util.Log
import io.ktor.client.features.logging.*

class AndroidLogger : Logger {
    override fun log(message: String) {
        Log.i("okhttp", message)
    }
}