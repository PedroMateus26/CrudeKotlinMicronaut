package com.pedromateus.zup.controllers.dtos

import com.pedromateus.zup.entities.CadastradosParaBatalhaEntity
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class CadastroParaBatalhaRequestDTO(
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

    constructor(soldadoCadastrado: CadastradosParaBatalhaEntity) : this(
        soldadoCadastrado.classe!!,
        soldadoCadastrado.raca!!,
        soldadoCadastrado.profissao!!
    )


    fun toCadastradoEntity() = CadastradosParaBatalhaEntity(
        classe = classe,
        raca = raca,
        profissao = profissao
    )


}