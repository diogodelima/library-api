package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty

data class ChangePasswordRequestDto(

    @field:NotEmpty
    val password: String

)