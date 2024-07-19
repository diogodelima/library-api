package com.diogo.library.exceptions

class TokenExpiredException(

    override val message: String? = "Token already expired"

): RuntimeException(message)