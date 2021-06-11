package com.pedromateus.zup.service

import com.pedromateus.zup.controller.dto.BatalhaRequest
import com.pedromateus.zup.entity.BatalhaEntity
import javax.validation.Valid

interface BatalhaService {

    fun buscaSoldadoPeloId(id:Long): BatalhaRequest
    fun buscaTodosSoldados(): List<BatalhaEntity>
    fun cadastraSoldadoNoBanco(@Valid cadastrado: BatalhaEntity): BatalhaEntity?
    fun corrigeDadosDoSoldado(cadastrado: BatalhaEntity, id:Long): BatalhaRequest
    fun deletaSoldado(id:Long)
}