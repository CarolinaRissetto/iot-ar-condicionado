package iot.arduino.automacaoArCondicionado.services

import iot.arduino.automacaoArCondicionado.entity.AgendamentoEntity
import iot.arduino.automacaoArCondicionado.repositories.ArCondicionadoRepository
import iot.arduino.automacaoArCondicionado.request.AgendamentoRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Service
class AgendamentoService(
    private val arCondicionadoRepository: ArCondicionadoRepository,
    private val ligarArCondicionadoService: LigarArCondicionadoService,
    private val desligarArCondicionadoService: DesligarArCondicionadoService
) {

    fun agendarAgendamento(request: AgendamentoRequest) {
        val agendamento = AgendamentoEntity().apply {
            horaLigamento = request.horaLigamento
            horaDesligamento = request.horaDesligamento
        }
        arCondicionadoRepository.save(agendamento)
    }

    @Scheduled(cron = "0 * * * * *")
    fun verificarAgendamento() {
        val now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
        val agendamento = arCondicionadoRepository.findAll().firstOrNull()

        if (agendamento != null) {
            if (agendamento.horaLigamento == now) {
                ligarArCondicionadoService.ligar()
            } else if (agendamento.horaDesligamento == now) {
                desligarArCondicionadoService.desligar()
            }
        }
    }
}
