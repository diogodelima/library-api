package com.diogo.library.dto

import kotlinx.serialization.SerialName

data class PageResponseDto<T>(

    val data: Collection<T>,

    val page: Int,

    @SerialName("page_size")
    val pageSize: Int,

    @SerialName("total_pages")
    val totalPages: Int

)