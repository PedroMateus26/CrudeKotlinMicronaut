package com.pedromateus.zup.controller.dtos

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
class CadastroParaBatalhaRequestDTO(
    @field:NotBlank(message = "Nome da classe é obrigatório")
    @field:NotNull(message = "Nome da classe é obrigatório")
    val classe:String,

    @field:NotNull(message = "Nome da raça é obrigatório")
    @field:NotBlank(message = "Nome da raça é obrigatório")
    val raca:String,

    @field:NotNull(message = "Nome da profissao é obrigatório")
    @field:NotBlank(message = "Nome da profissao é obrigatório")
    val profissao:String,
) {

}