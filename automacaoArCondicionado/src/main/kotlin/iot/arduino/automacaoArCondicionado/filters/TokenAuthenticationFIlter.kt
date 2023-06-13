package iot.arduino.automacaoArCondicionado.filters

import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import iot.arduino.automacaoArCondicionado.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenAuthenticationFilter(
    private val repository: UsuarioRepository,
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenFromHeader = getTokenFromHeader(request)
        val tokenValid: Boolean = tokenService.isTokenValid(tokenFromHeader)
        if (tokenValid) {
            authenticate(tokenFromHeader)
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath
        return path.startsWith("/auth") || path.startsWith("/usuario")
    }

    private fun authenticate(tokenFromHeader: String?) {
        val username = tokenService.getUsername(tokenFromHeader)
        val usuario = repository.findByUsername(username)
        if (usuario != null) {
            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }
    }

    private fun getTokenFromHeader(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        return if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            null
        } else token.substring(7, token.length)
    }
}