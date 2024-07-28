package com.diogo.library.dto

import kotlinx.serialization.SerialName
import org.jetbrains.annotations.NotNull

data class AuthorUpdateRequestDto(

    @field:NotNull
    val id: Long,

    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    val biography: String? = null,

)