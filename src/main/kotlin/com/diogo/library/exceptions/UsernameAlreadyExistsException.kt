package com.diogo.library.exceptions

class UsernameAlreadyExistsException(

    override val message: String? = "Username already exists."

): RuntimeException(message)