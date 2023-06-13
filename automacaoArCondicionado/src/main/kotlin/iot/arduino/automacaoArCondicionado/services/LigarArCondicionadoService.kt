package iot.arduino.automacaoArCondicionado.services

import lombok.AllArgsConstructor
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.SocketException

@Service
@AllArgsConstructor
class LigarArCondicionadoService(
    authenticationService: AuthenticationService,
    httpClient: OkHttpClient
) : ArCondicionadoService(
    authenticationService, httpClient
) {
    fun ligar() {
        ligar(authenticationService.getCurrentUser().ip!!)
    }

    fun ligar(ip: String) {

        val param = "/estado?params=1"
        val (request, client) = montarRequest(param, ip)
        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            if (e is SocketException) {
                tratarErro(e)
            }
            e.printStackTrace()
        }
    }
}
