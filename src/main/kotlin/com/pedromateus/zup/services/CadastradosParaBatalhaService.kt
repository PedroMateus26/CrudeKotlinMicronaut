package com.pedromateus.zup.services

import com.pedromateus.zup.controllers.dtos.CadastroParaBatalhaRequestDTO
import com.pedromateus.zup.entities.CadastradosParaBatalhaEntity
import com.pedromateus.zup.repositories.CadastroBatalhaRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

@Validated
@Singleton
class CadastradosParaBatalhaService (
    private val repository:CadastroBatalhaRepository
    ){

    val LOGGER= LoggerFactory.getLogger(this::class.java)

    fun buscaSoldadoCadastradoPeloId(id:Long): CadastroParaBatalhaRequestDTO{
       val soldadoCadastrado= repository.findById(id).orElseThrow{
           throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
       }
        return CadastroParaBatalhaRequestDTO(soldadoCadastrado)
    }

    fun buscaTodosSoldadosCadastrados(): List<CadastradosParaBatalhaEntity> {
        return repository.findAll()
    }

    fun cadastraSoldadoNoBanco(@Valid cadastrado:CadastradosParaBatalhaEntity): CadastroParaBatalhaRequestDTO {
        val soldadoCadastrado=repository.save(cadastrado)
        return CadastroParaBatalhaRequestDTO(soldadoCadastrado)
    }

    fun corrigeDadosDoSoldadoCadastrado(cadastrado:CadastradosParaBatalhaEntity,id:Long):CadastroParaBatalhaRequestDTO {
        var soldadoCadastrado=repository.findById(id).orElseThrow{
            throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
        }

        soldadoCadastrado.classe=cadastrado.classe
        soldadoCadastrado.raca=cadastrado.raca
        soldadoCadastrado.profissao=cadastrado.profissao

        soldadoCadastrado=repository.update(soldadoCadastrado)

        return CadastroParaBatalhaRequestDTO(soldadoCadastrado)
    }

    fun deletaSoldadoCadastrado(id:Long){

        val soldadoCadastrado=repository.findById(id).orElseThrow{
            throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
        }
        repository.delete(soldadoCadastrado)
    }
}