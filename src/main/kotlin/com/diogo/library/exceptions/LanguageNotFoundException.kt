package com.diogo.library.exceptions

class LanguageNotFoundException(

    override val message: String? = "Language not found"

): RuntimeException(message)