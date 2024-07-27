package com.diogo.library.exceptions

class AuthorNotFoundException(

    override val message: String? = "Author not found"

): RuntimeException(message)