package com.pedromateus.zup.controller

import com.pedromateus.zup.controller.dto.BatalhaRequest
import com.pedromateus.zup.service.BatalhaService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/soldados")
class CadastroBatalhaController (private val serviceImp: BatalhaService){

    @Get
    fun buscaTodosSoldadosCcadastrados(): HttpResponse<Any> {
        return HttpResponse.ok(serviceImp.buscaTodosSoldados())
    }

    @Get("/{id}")
    fun buscaTodosSoldadosCcadastrados(@PathVariable id:Long): HttpResponse<Any> {
        return HttpResponse.ok(serviceImp.buscaSoldadoPeloId(id))
    }

    @Post
    fun cadastroParaBatalha(@Valid @Body cadastro: BatalhaRequest):HttpResponse<Any>{
        val soldadoCadastrado=serviceImp.cadastraSoldadoNoBanco(cadastro.toCadastradoEntity())
        return HttpResponse.created(location(soldadoCadastrado!!.id!!))
    }

    @Put("/{id}")
    fun cadastroParaBatalha(@Valid @Body cadastro: BatalhaRequest, @PathVariable id:Long):HttpResponse<Any>{
        val soldadoCadastrado=serviceImp.corrigeDadosDoSoldado(cadastro.toCadastradoEntity(),id)

        return HttpResponse.ok(soldadoCadastrado)
    }

    @Delete("/{id}")
    fun deletaSoldadoCadastrado(id:Long):HttpResponse<Any>{
        val soldadoCadastrado=serviceImp.deletaSoldado(id)

        return HttpResponse.noContent()
    }

    private fun location(id:Long)=HttpResponse.uri("/soldados/$id")

}