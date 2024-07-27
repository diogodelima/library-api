package com.diogo.library.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookCreateRequestDto(

    @field:NotEmpty
    val title: String,

    @field:NotEmpty
    val isbn: String,

    @field:NotEmpty
    @SerialName("release_date")
    val releaseDate: String,

    @field:NotEmpty
    val synopsis: String,

    @field:NotEmpty
    val language: String,

    @field:NotEmpty
    val publisher: String,

    @field:NotEmpty
    val collection: String,

    @field:NotNull
    @SerialName("author_id")
    val authorId: Long

)