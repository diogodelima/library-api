package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorCreateRequestDto(

    @field:NotEmpty
    @SerialName("first_name")
    val firstName: String,

    @field:NotEmpty
    @SerialName("last_name")
    val lastName: String,

    @field:NotEmpty
    val biography: String

)