package com.pedromateus.zup.services

import com.pedromateus.zup.controllers.dtos.CadastroParaBatalhaRequestDTO
import com.pedromateus.zup.entities.CadastradosParaBatalhaEntity
import com.pedromateus.zup.repositories.CadastroBatalhaRepository
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class CadastradosParaBatalhaService (
    private val repository:CadastroBatalhaRepository
    ){

    val LOGGER= LoggerFactory.getLogger(this::class.java)

    fun cadastraSoldadoNoBanco(cadastrado:CadastradosParaBatalhaEntity): CadastroParaBatalhaRequestDTO {
        val soldadoCadastrado=repository.save(cadastrado)
        LOGGER.info(soldadoCadastrado.toString())
        return CadastroParaBatalhaRequestDTO(soldadoCadastrado)
    }
}