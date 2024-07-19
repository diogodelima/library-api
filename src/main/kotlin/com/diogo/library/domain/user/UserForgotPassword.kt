package com.diogo.library.domain.user

import jakarta.persistence.*
import java.time.Instant
import java.util.Date

@Entity
@Table(name = "user_forgot_password")
data class UserForgotPassword(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    val expirationDate: Date = Date.from(Instant.now().plusSeconds(TOKEN_EXPIRE_DELAY))

){

    companion object {

        private const val TOKEN_EXPIRE_DELAY = 60 * 30L

    }

}