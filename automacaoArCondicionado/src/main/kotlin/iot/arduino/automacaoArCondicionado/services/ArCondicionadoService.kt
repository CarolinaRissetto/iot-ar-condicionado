package iot.arduino.automacaoArCondicionado.services

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.http.HttpStatus
import java.net.NoRouteToHostException
import java.net.SocketException
import java.net.SocketTimeoutException

class AppException(
    val status: HttpStatus,
    message: String
) : RuntimeException(message)

abstract class ArCondicionadoService(
    protected val authenticationService: AuthenticationService,
    protected val httpClient: OkHttpClient
) {

    private val MEDIA_TYPE = "text/plain".toMediaType()
    private val BODY = "".toRequestBody(MEDIA_TYPE)

    fun montarRequest(param: String, ip: String): Pair<Request, OkHttpClient> {
        var ip = ip
        if (!ip.startsWith("http")) {
            ip = "http://$ip"
        }
        val url = ip + param

        val request = Request.Builder()
            .url(url)
            .post(BODY)
            .build()

        return Pair(request, httpClient)
    }

    fun tratarErro(exception: SocketException) {
        when (exception) {
            is NoRouteToHostException -> throw AppException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Dispositivo IOT indisponivel"
            )

            is SocketTimeoutException -> throw AppException(
                HttpStatus.TOO_MANY_REQUESTS,
                "Dispositivo lento ou com excesso de requisições"
            )
        }

    }
}