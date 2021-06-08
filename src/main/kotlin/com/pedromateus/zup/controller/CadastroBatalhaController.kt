package com.pedromateus.zup.controller

import com.pedromateus.zup.controller.dtos.CadastroParaBatalhaRequestDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.Validator

@Validated
@Controller("/soldados")
class CadastroBatalhaController (private val validator: Validator){

    @Post
    fun cadastroParaBatalha(@Valid @Body cadastro: CadastroParaBatalhaRequestDTO):HttpResponse<Any>{
        return HttpResponse.ok(cadastro)
    }
}