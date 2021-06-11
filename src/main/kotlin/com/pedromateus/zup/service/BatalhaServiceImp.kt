package com.pedromateus.zup.service

import com.pedromateus.zup.controller.dto.BatalhaRequest
import com.pedromateus.zup.entity.BatalhaEntity
import com.pedromateus.zup.repository.BatalhaRepository
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

@Validated
@Singleton
class BatalhaServiceImp (
    private val repository: BatalhaRepository
    ):BatalhaService{

    val LOGGER= LoggerFactory.getLogger(this::class.java)


    override fun buscaSoldadoPeloId(id:Long): BatalhaRequest {
       val soldadoCadastrado= repository.findById(id).orElseThrow{
           throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
       }
        return BatalhaRequest(soldadoCadastrado)
    }

    override fun buscaTodosSoldados(): List<BatalhaEntity> {
        return repository.findAll()
    }

    override fun cadastraSoldadoNoBanco(@Valid cadastrado: BatalhaEntity): BatalhaEntity? {
        val soldadoCadastrado=repository.save(cadastrado)
        return soldadoCadastrado
    }

    override fun corrigeDadosDoSoldado(cadastrado: BatalhaEntity, id:Long): BatalhaRequest {
        var soldadoCadastrado=repository.findById(id).orElseThrow{
            throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
        }

        soldadoCadastrado.classe=cadastrado.classe
        soldadoCadastrado.raca=cadastrado.raca
        soldadoCadastrado.profissao=cadastrado.profissao

        soldadoCadastrado=repository.update(soldadoCadastrado)

        return BatalhaRequest(soldadoCadastrado)
    }

    override fun deletaSoldado(id:Long){

        val soldadoCadastrado=repository.findById(id).orElseThrow{
            throw EntityNotFoundException("Entidade não encontrada com o id fornecido!")
        }
        repository.delete(soldadoCadastrado)
    }
}