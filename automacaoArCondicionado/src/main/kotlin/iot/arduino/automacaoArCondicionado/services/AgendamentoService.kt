package iot.arduino.automacaoArCondicionado.services

import iot.arduino.automacaoArCondicionado.entity.AgendamentoEntity
import iot.arduino.automacaoArCondicionado.repositories.ArCondicionadoRepository
import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import iot.arduino.automacaoArCondicionado.request.AgendamentoRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Service
class AgendamentoService(
    private val arCondicionadoRepository: ArCondicionadoRepository,
    private val ligarArCondicionadoService: LigarArCondicionadoService,
    private val desligarArCondicionadoService: DesligarArCondicionadoService,
    private val authenticationService: AuthenticationService,
    private val usuarioRepository: UsuarioRepository
) {

    fun agendarAgendamento(request: AgendamentoRequest) {
        var user = authenticationService.getCurrentUser()
        val agendamentoExistente = arCondicionadoRepository.findByUsuario(user.username)

        if (agendamentoExistente != null) {
            agendamentoExistente.horaLigamento = request.horaLigamento
            agendamentoExistente.horaDesligamento = request.horaDesligamento

            arCondicionadoRepository.save(agendamentoExistente)

        } else {
            val agendamento = AgendamentoEntity().apply {
                horaLigamento = request.horaLigamento
                horaDesligamento = request.horaDesligamento
                usuario = user.username
            }

            arCondicionadoRepository.save(agendamento)
        }
    }

    @Scheduled(cron = "0 * * * * *")
    fun verificarAgendamento() {
        val now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
        arCondicionadoRepository.findAll().forEach {
            val user = usuarioRepository.findByUsername(it.usuario)

            if (user == null || user.ip == null) {
                return;
            }

            if (it.horaLigamento == now) {
                ligarArCondicionadoService.ligar(user.ip)
            } else if (it.horaDesligamento == now) {
                desligarArCondicionadoService.desligar(user.ip)
            }
        }
    }
}
