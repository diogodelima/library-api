package com.diogo.library.exceptions.handler

import com.diogo.library.exceptions.*
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

    @ExceptionHandler(
        UsernameNotFoundException::class, UsernameAlreadyExistsException::class, EmailAlreadyExistsException::class,
        TokenExpiredException::class, DateFormatException::class, PageInvalidException::class
    )
    fun generalExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(
        BookNotFoundException::class, TokenNotFoundException::class, AuthorNotFoundException::class,
        LanguageNotFoundException::class
    )
    fun notFoundExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(com.auth0.jwt.exceptions.TokenExpiredException::class)
    fun loginTokenExpiredExceptionHandler(ex: Exception, request: WebRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(ex, "You login token is expired", HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

}