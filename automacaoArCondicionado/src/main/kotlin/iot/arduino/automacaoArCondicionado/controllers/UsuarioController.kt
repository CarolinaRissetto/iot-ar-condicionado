package iot.arduino.automacaoArCondicionado.controllers

import iot.arduino.automacaoArCondicionado.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

data class UsuarioRequest(val nome: String, val senha: String, val ip: String)

@RestController
@RequestMapping("/usuario")
class UsuarioController(
    private val usuarioService: UsuarioService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun cadastrar(@RequestBody request: UsuarioRequest) {
        usuarioService.cadastrar(request.nome, request.senha, request.ip)
    }
}
