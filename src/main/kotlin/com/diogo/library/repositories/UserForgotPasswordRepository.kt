package com.diogo.library.repositories

import com.diogo.library.domain.user.UserForgotPassword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface UserForgotPasswordRepository: JpaRepository<UserForgotPassword, String> {

    fun deleteByExpirationDateLessThan(date: Date)

}