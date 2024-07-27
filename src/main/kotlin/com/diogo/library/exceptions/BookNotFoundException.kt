package com.diogo.library.exceptions

class BookNotFoundException(

    override val message: String? = "Book not found"

): RuntimeException(message)