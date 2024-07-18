package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty

data class LoginRequestDto(

    @field:NotEmpty
    val username: String,

    @field:NotEmpty
    val password: String

)