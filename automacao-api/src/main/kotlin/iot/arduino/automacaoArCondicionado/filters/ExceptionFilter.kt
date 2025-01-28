package iot.arduino.automacaoArCondicionado.filters

import com.fasterxml.jackson.databind.ObjectMapper
import iot.arduino.automacaoArCondicionado.services.AppException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

data class Error(val status: Int, val message: String)

@Component
class ExceptionHandlerFilter : OncePerRequestFilter(

) {
    @Throws(ServletException::class, IOException::class)
    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AppException) {
            response.status = e.status.value()
            response.writer.write(
                ObjectMapper().writeValueAsString(
                    Error(
                        e.status.value(),
                        e.message ?: "Tente novamente mais tarde"
                    )
                )
            )
        }
    }
}