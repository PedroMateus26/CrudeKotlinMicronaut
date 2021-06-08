package com.pedromateus.zup.repositories
import com.pedromateus.zup.entities.CadastradosParaBatalhaEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface CadastroBatalhaRepository:JpaRepository<CadastradosParaBatalhaEntity,Long>{
}