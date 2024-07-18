package com.diogo.library.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.diogo.library.domain.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtService(

    @Value("\${api.security.token.secret}")
    val secret: String

) {

    fun generateToken(user: User): String {

        val algorithm = Algorithm.HMAC256(secret)

        return JWT.create()
            .withIssuer("library-api")
            .withSubject(user.username)
            .withExpiresAt(expiresAt())
            .sign(algorithm)
    }

    fun validateToken(token: String): String {

        val algorithm = Algorithm.HMAC256(secret)

        return JWT.require(algorithm)
            .withIssuer("library-api")
            .build()
            .verify(token)
            .subject
    }

    fun expiresAt(): Instant {
        return Instant.now().plusSeconds(3600L)
    }

}