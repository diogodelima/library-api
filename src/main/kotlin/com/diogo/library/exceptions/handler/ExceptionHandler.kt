package com.diogo.library.exceptions.handler

import com.diogo.library.exceptions.EmailAlreadyExistsException
import com.diogo.library.exceptions.UsernameAlreadyExistsException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(UsernameNotFoundException::class, UsernameAlreadyExistsException::class, EmailAlreadyExistsException::class)
    fun usernameNotFoundExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}