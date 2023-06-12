package iot.arduino.automacaoArCondicionado.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.time.LocalTime

@Entity
class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Setter
    @Column(nullable = false)
    var horaLigamento: LocalTime? = null

    @Setter
    @Column(nullable = false)
    var horaDesligamento: LocalTime? = null
}