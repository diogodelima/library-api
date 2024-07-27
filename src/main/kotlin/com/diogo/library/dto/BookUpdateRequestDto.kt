package com.diogo.library.dto

import jakarta.validation.constraints.NotNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookUpdateRequestDto(

    @field:NotNull
    val id: Long,

    val title: String? = null,

    val isbn: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    val synopsis: String? = null,

    val language: String? = null,

    val publisher: String? = null,

    val collection: String? = null,

    @SerialName("author_id")
    val authorId: Long? = null

)