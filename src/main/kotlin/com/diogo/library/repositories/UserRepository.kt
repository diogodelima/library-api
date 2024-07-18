package com.diogo.library.repositories

import com.diogo.library.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String> {

    fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?

}