package iot.arduino.automacaoArCondicionado.config

import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import iot.arduino.automacaoArCondicionado.services.UsuarioService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStart(
    private val usuarioService: UsuarioService,
    private val usuarioRepository: UsuarioRepository
) : ApplicationListener<ApplicationReadyEvent?> {

    @Value("\${api.usuario-padrao}")
    private lateinit var usuario: String

    @Value("\${api.senha-padrao}")
    private lateinit var senha: String

    @Value("\${api.ip-padrao}")
    private lateinit var ip: String
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        if (usuarioRepository.findByUsername(usuario) == null) {
            usuarioService.cadastrar(usuario, senha, ip)
        }
    }
}