package com.diogo.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageBookResponseDto(

    val books: Collection<BookResponseDto>,

    val page: Int,

    @SerialName("page_size")
    val pageSize: Int,

    @SerialName("total_pages")
    val totalPages: Int

)