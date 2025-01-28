package iot.arduino.automacaoArCondicionado.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
class OkHttp {

    @Bean
    fun client(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(1, TimeUnit.MINUTES)
        builder.readTimeout(1, TimeUnit.MINUTES)
        builder.writeTimeout(1, TimeUnit.MINUTES)
        return builder.build()
    }
}