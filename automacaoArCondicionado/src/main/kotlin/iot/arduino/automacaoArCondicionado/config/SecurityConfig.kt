package iot.arduino.automacaoArCondicionado.config

import iot.arduino.automacaoArCondicionado.filters.ExceptionHandlerFilter
import iot.arduino.automacaoArCondicionado.filters.TokenAuthenticationFilter
import iot.arduino.automacaoArCondicionado.repositories.UsuarioRepository
import iot.arduino.automacaoArCondicionado.services.AuthenticationService
import iot.arduino.automacaoArCondicionado.services.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class SecurityConfig(
    private val authenticationService: AuthenticationService,
    private val repository: UsuarioRepository,
    private val tokenService: TokenService
) {

    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(http: HttpSecurity): AuthenticationManager? {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(authenticationProvider())
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider? {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(authenticationService)
        return provider
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests(
                Customizer { authz ->
                    authz
                        .requestMatchers("/auth").anonymous()
                        .requestMatchers("/usuario").anonymous()
                        .anyRequest().authenticated()
                }
            )
            .csrf { it.disable() }
            .cors { it.disable() }
            .httpBasic(withDefaults())
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(
                TokenAuthenticationFilter(
                    repository,
                    tokenService
                ), UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(ExceptionHandlerFilter(), TokenAuthenticationFilter::class.java)
        return http.build()
    }


}