package com.pedromateus.zup.shared.exception_handler

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Singleton
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolationException

@Singleton
class GlobalExceptionHandler:ExceptionHandler<Exception,HttpResponse<Any>> {

    val LOGGER=LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: Exception?): HttpResponse<Any> {
        val (httpStatus, message)=when(exception){
            is EntityNotFoundException->Pair(HttpStatus.NOT_FOUND, exception.message)
            is ConstraintViolationException->Pair(HttpStatus.BAD_REQUEST,exception.message)
            is IllegalArgumentException->Pair(HttpStatus.UNPROCESSABLE_ENTITY, exception.message)
            else->Pair(HttpStatus.I_AM_A_TEAPOT, "Deu ruim! Fuja para as montanhas.")
        }
        return HttpResponse.status<JsonError>(httpStatus).body(JsonError(message))
    }
}