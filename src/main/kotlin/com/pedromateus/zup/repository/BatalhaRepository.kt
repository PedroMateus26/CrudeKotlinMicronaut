package com.pedromateus.zup.repository
import com.pedromateus.zup.entity.BatalhaEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface BatalhaRepository:JpaRepository<BatalhaEntity,Long>{
}