package com.diogo.library.services

import com.diogo.library.domain.user.UserForgotPassword
import com.diogo.library.repositories.UserForgotPasswordRepository
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class UserForgotPasswordService(

    val userForgotPasswordRepository: UserForgotPasswordRepository

) {

    companion object {

        private const val DELETE_EXPIRED_TOKEN_DELAY = 3600 * 1000L

    }

    @Scheduled(fixedRate = DELETE_EXPIRED_TOKEN_DELAY)
    @Transactional
    fun cleanUpExpiredTokens() {
        val now = Date.from(Instant.now())
        userForgotPasswordRepository.deleteByExpirationDateLessThan(now)
    }

    fun delete(id: String) {
        userForgotPasswordRepository.deleteById(id)
    }

    fun save(userForgotPassword: UserForgotPassword): UserForgotPassword {
        return userForgotPasswordRepository.save(userForgotPassword)
    }

    fun getById(id: String): UserForgotPassword? {
        return userForgotPasswordRepository.findById(id).orElse(null)
    }

}