package com.diogo.library.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class BookResponseDto(

    val id: Long,

    val title: String,

    val isbn: String,

    @Contextual
    @SerialName("release_date")
    val releaseDate: LocalDate,

    val authorId: Long

)