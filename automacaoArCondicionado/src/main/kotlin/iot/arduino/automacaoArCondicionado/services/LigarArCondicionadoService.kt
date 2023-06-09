package iot.arduino.automacaoArCondicionado.services

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.io.IOException

@Service
@AllArgsConstructor
class LigarArCondicionadoService : ArCondicionadoService() {

    fun ligar() {

        val param = "/estado?params=1"
        val (request, client) = montarRequest(param)
        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
