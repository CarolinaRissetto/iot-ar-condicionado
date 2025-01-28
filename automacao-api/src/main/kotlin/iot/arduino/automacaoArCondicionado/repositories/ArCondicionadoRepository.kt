package iot.arduino.automacaoArCondicionado.repositories

import iot.arduino.automacaoArCondicionado.entity.AgendamentoEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface ArCondicionadoRepository : CrudRepository<AgendamentoEntity, Long> {
    fun findByUsuario(usuario: String): AgendamentoEntity?
}
