package iot.arduino.automacaoArCondicionado.services

import iot.arduino.automacaoArCondicionado.entity.UsuarioEntity
import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val passwordEncoder: PasswordEncoder,
    private val usuarioRepository: UsuarioRepository
) {

    fun cadastrar(nome: String, senha: String, ip: String) {
        val usuario = UsuarioEntity(
            nome,
            passwordEncoder.encode(senha),
            ip
        )

        usuarioRepository.save(usuario)
    }
}