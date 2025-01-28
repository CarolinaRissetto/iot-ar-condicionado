package iot.arduino.automacaoArCondicionado.services

import lombok.AllArgsConstructor
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.SocketException

@Service
@AllArgsConstructor
class DesligarArCondicionadoService(authenticationService: AuthenticationService, httpClient: OkHttpClient) :
    ArCondicionadoService(
        authenticationService, httpClient
    ) {
    fun desligar() {
        desligar(authenticationService.getCurrentUser().ip!!)
    }

    fun desligar(ip: String) {

        val param = "/estado?params=0"
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