package iot.arduino.automacaoArCondicionado.repositories

import iot.arduino.automacaoArCondicionado.entity.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<UsuarioEntity, Long?> {
    fun findByUsername(username: String?): UsuarioEntity?
}