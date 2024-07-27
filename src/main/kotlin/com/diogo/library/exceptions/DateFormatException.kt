package com.diogo.library.exceptions

class DateFormatException(

    override val message: String? = "Wrong date format"

): RuntimeException(message)