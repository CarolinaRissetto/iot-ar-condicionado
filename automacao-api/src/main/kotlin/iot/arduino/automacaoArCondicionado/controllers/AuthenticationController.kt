package iot.arduino.automacaoArCondicionado.controllers

import iot.arduino.automacaoArCondicionado.request.LoginRequest
import iot.arduino.automacaoArCondicionado.response.LoginResponse
import iot.arduino.automacaoArCondicionado.services.TokenService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
) {
    @PostMapping
    fun auth(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val usernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(request.username, request.senha)
        val authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        val token = tokenService.generateToken(authentication)
        return ResponseEntity.ok(LoginResponse(token))
    }
}