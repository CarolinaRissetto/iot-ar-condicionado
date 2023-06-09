package iot.arduino.automacaoArCondicionado.request

import iot.arduino.automacaoArCondicionado.enum.Operacao

data class ArCondicionadoRequest(val operacao: Operacao, val temperatura: Int) {
}