package iot.arduino.automacaoArCondicionado.controllers

import iot.arduino.automacaoArCondicionado.entity.AgendamentoEntity
import iot.arduino.automacaoArCondicionado.enum.Operacao.*
import iot.arduino.automacaoArCondicionado.request.AgendamentoRequest
import iot.arduino.automacaoArCondicionado.request.ArCondicionadoRequest
import iot.arduino.automacaoArCondicionado.services.AgendamentoService
import iot.arduino.automacaoArCondicionado.services.DesligarArCondicionadoService
import iot.arduino.automacaoArCondicionado.services.LigarArCondicionadoService
import iot.arduino.automacaoArCondicionado.services.TemperaturaArCondicionadoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/ar-condicionado")
class ArCondicionadoController(
    private val ligarService: LigarArCondicionadoService,
    private val desligarService: DesligarArCondicionadoService,
    private val temperaturaService: TemperaturaArCondicionadoService,
    private val agendamentoService: AgendamentoService
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/operacao")
    fun operacao(@RequestBody request: ArCondicionadoRequest) {
        when (request.operacao) {
            LIGAR -> ligarService.ligar()
            DESLIGAR -> desligarService.desligar()
            ALTERAR_TEMPERATURA -> temperaturaService.alterar(request.temperatura)
        }
    }

    @PostMapping("/agendamento")
    @ResponseStatus(HttpStatus.OK)
    fun criarAgendamento(@RequestBody request: AgendamentoRequest) {
        agendamentoService.agendarAgendamento(request)
    }

    @DeleteMapping("/agendamento")
    @ResponseStatus(HttpStatus.OK)
    fun deletarAgendamento() {
        agendamentoService.deletarAgendamento()
    }

    @GetMapping("/agendamento")
    @ResponseStatus(HttpStatus.OK)
    fun obterAgendamento(): AgendamentoEntity? {
       return agendamentoService.obterAgendamento()
    }
}