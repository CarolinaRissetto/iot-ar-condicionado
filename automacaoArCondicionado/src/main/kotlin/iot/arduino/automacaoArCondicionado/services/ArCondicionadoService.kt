package iot.arduino.automacaoArCondicionado.services

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

abstract class ArCondicionadoService {

    val BASE_URL = "http://000.000.0.000:8080"
    val MEDIA_TYPE = "text/plain".toMediaType()
    val BODY = "".toRequestBody(MEDIA_TYPE)

    fun montarRequest(param: String): Pair<Request, OkHttpClient> {
        val url = BASE_URL + param

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(BODY)
            .build()

        return Pair(request, client)
    }
}