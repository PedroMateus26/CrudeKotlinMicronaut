package com.pedromateus.zup.controllers

import com.pedromateus.zup.controllers.dtos.CadastroParaBatalhaRequestDTO
import com.pedromateus.zup.entities.CadastradosParaBatalhaEntity
import com.pedromateus.zup.services.CadastradosParaBatalhaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.lang.IllegalArgumentException
import java.util.*
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.Validator

@Validated
@Controller("/soldados")
class CadastroBatalhaController (private val service: CadastradosParaBatalhaService){

    @Get
    fun buscaTodosSoldadosCcadastrados(): HttpResponse<Any> {
        return HttpResponse.ok(service.buscaTodosSoldadosCadastrados())
    }

    @Get("/{id}")
    fun buscaTodosSoldadosCcadastrados(@PathVariable id:Long): HttpResponse<Any> {
        return HttpResponse.ok(service.buscaSoldadoCadastradoPeloId(id))
    }

    @Post
    fun cadastroParaBatalha(@Valid @Body cadastro: CadastroParaBatalhaRequestDTO):HttpResponse<Any>{
        val soldadoCadastrado=service.cadastraSoldadoNoBanco(cadastro.toCadastradoEntity())
        return HttpResponse.created(location(soldadoCadastrado!!.id!!))
    }

    @Put("/{id}")
    fun cadastroParaBatalha(@Valid @Body cadastro: CadastroParaBatalhaRequestDTO, @PathVariable id:Long):HttpResponse<Any>{
        val soldadoCadastrado=service.corrigeDadosDoSoldadoCadastrado(cadastro.toCadastradoEntity(),id)

        return HttpResponse.ok(soldadoCadastrado)
    }

    @Delete("/{id}")
    fun deletaSoldadoCadastrado(id:Long):HttpResponse<Any>{
        val soldadoCadastrado=service.deletaSoldadoCadastrado(id)

        return HttpResponse.noContent()
    }

    private fun location(id:Long)=HttpResponse.uri("/soldados/$id")

}