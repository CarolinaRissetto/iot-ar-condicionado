package iot.arduino.automacaoArCondicionado.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
class UsuarioEntity(
    private val username: String? = null,
    private val pass: String? = null,
    ip: String? = null
) : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    val ip = ip

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(
            SimpleGrantedAuthority("READ_PRIVILEGE"),
            SimpleGrantedAuthority("WRITE_PRIVILEGE")
        )
    }

    override fun getPassword(): String {
        return pass!!
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }
}