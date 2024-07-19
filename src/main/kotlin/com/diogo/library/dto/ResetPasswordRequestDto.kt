package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty

data class ResetPasswordRequestDto(

    @field:NotEmpty
    val token: String,

    @field:NotEmpty
    val password: String

)