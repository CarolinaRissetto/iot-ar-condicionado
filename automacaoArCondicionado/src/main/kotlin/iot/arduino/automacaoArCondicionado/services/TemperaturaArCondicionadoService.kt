package iot.arduino.automacaoArCondicionado.services

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.io.IOException

@Service
@AllArgsConstructor
class TemperaturaArCondicionadoService : ArCondicionadoService() {

    fun alterar(param: Int) {

        val param = "/temperatura?params=$param"
        val (request, client) = montarRequest(param)

        try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}