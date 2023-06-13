package iot.arduino.automacaoArCondicionado.services

import iot.arduino.automacaoArCondicionado.entity.UsuarioEntity
import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AuthenticationService : UserDetailsService {
    @Autowired
    private lateinit var repository: UsuarioRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = repository.findByUsername(username)
        if (usuario == null) {
            throw UsernameNotFoundException("Usuario nao encontrado")
        }
        return usuario
    }

    fun getCurrentUser(): UsuarioEntity {
        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails

        return repository.findByUsername(principal.username)!!
    }
}