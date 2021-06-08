package com.pedromateus.zup.controllers

import com.pedromateus.zup.controllers.dtos.CadastroParaBatalhaRequestDTO
import com.pedromateus.zup.services.CadastradosParaBatalhaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.Validator

@Validated
@Controller("/soldados")
class CadastroBatalhaController (private val service: CadastradosParaBatalhaService){

    @Post
    fun cadastroParaBatalha(@Valid @Body cadastro: CadastroParaBatalhaRequestDTO):HttpResponse<Any>{
        val soldadoCadastrado=service.cadastraSoldadoNoBanco(cadastro.toCadastradoEntity())

        return HttpResponse.created(location())
    }

    private fun location()=HttpResponse
        .uri("/soldados")
}