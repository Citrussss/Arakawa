package top.arakawa.ktor

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import top.arakawa.ktor.logger.AndroidLogger

object Http {
    public val httpClient = HttpClient(Android) {
        defaultRequest{
            host = "www.wanandroid.com"
            url.protocol = URLProtocol.HTTPS
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = MessageLengthLimitingLogger(delegate = AndroidLogger())
            level = LogLevel.ALL
        }
        engine {

        }
//        engine {
//            connectTimeout = 100_000
//            socketTimeout = 100_000
//        }
    }
}