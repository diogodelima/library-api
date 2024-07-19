package com.diogo.library.dto

import java.util.Date

data class ForgotPasswordResponseDto(

    val user: String,
    val expiresAt: Date

)