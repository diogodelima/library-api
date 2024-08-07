package com.diogo.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponseDto(

    val id: Long,

    val title: String,

    val isbn: String,

    @SerialName("release_date")
    val releaseDate: String,

    val synopsis: String,

    val language: String,

    val publisher: String,

    val collection: String,

    @SerialName("author_id")
    val authorId: Long

)