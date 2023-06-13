package iot.arduino.automacaoArCondicionado.services

import lombok.AllArgsConstructor
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.SocketException

@Service
@AllArgsConstructor
class TemperaturaArCondicionadoService(authenticationService: AuthenticationService, httpClient: OkHttpClient) :
    ArCondicionadoService(
        authenticationService, httpClient
    ) {
    fun alterar(param: Int) {

        val param = "/temperatura?params=$param"
        val (request, client) = montarRequest(param, authenticationService.getCurrentUser().ip!!)

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