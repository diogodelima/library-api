package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty

data class RegisterRequestDto(

    @field:NotEmpty
    val email: String,

    @field:NotEmpty
    val username: String,

    @field:NotEmpty
    val password: String

)