package com.pedromateus.zup.controller.dto

import com.pedromateus.zup.entity.BatalhaEntity
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class BatalhaRequest(
    @field:NotBlank(message = "Nome da classe é obrigatório")
    @field:NotNull(message = "Nome da classe é obrigatório")
    val classe: String,

    @field:NotNull(message = "Nome da raça é obrigatório")
    @field:NotBlank(message = "Nome da raça é obrigatório")
    val raca: String,

    @field:NotNull(message = "Nome da profissao é obrigatório")
    @field:NotBlank(message = "Nome da profissao é obrigatório")
    val profissao: String,
) {

    constructor(soldadoCadastrado: BatalhaEntity) : this(
        soldadoCadastrado.classe!!,
        soldadoCadastrado.raca!!,
        soldadoCadastrado.profissao!!
    )


    fun toCadastradoEntity() = BatalhaEntity(
        classe = classe,
        raca = raca,
        profissao = profissao
    )


}