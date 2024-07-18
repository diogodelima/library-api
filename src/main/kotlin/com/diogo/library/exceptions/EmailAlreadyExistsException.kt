package com.diogo.library.exceptions

class EmailAlreadyExistsException(

    override val message: String? = "Email already exists"

): RuntimeException(message)