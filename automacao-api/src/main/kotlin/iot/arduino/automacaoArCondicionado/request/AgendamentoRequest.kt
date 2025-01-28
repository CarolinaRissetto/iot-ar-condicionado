package iot.arduino.automacaoArCondicionado.request

import java.time.LocalTime

data class AgendamentoRequest(
    val horaLigamento: LocalTime,
    val horaDesligamento: LocalTime
)