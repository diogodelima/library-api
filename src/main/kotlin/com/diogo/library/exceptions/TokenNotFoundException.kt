package com.diogo.library.exceptions

class TokenNotFoundException(

    override val message: String? = "token not found"

): RuntimeException(message)