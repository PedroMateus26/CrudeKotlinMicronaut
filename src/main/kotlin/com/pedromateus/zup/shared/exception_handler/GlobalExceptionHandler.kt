package com.pedromateus.zup.shared.exception_handler

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.slf4j.LoggerFactory
import java.lang.Exception
import javax.inject.Singleton

@Singleton
class GlobalExceptionHandler:ExceptionHandler<Exception,HttpResponse<Any>> {

    val LOGGER=LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: Exception?): HttpResponse<Any> {

        val statusName=exception!!.javaClass.simpleName
        val statusDescription=exception!!.message

        LOGGER.info("$statusName, $statusDescription")
        println(exception)
        return HttpResponse.ok()
    }
}