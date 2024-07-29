package com.diogo.library.exceptions

class PageInvalidException(

    override val message: String? = "Page number should be positive"

): RuntimeException(message)