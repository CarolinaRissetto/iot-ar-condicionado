package iot.arduino.automacaoArCondicionado.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import iot.arduino.automacaoArCondicionado.entity.UsuarioEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*


@Service
class TokenService {

    @Value("\${jwt.expiration}")
    private val expiration: String? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null
    fun generateToken(authentication: Authentication): String {
        val usuario: UsuarioEntity = authentication.principal as UsuarioEntity
        val now = Date()
        val exp = Date(now.time + expiration!!.toLong())
        return Jwts.builder().setIssuer("Ar-condicionado")
            .setSubject(usuario.username)
            .setIssuedAt(Date())
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun isTokenValid(token: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsername(token: String?): String {
        val body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return body.subject
    }
}