package com.diogo.library.services

import com.diogo.library.domain.user.User
import com.diogo.library.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(

    val repository: UserRepository

): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        if (username == null)
            throw UsernameNotFoundException("Username can not be null")

        return repository.findByUsername(username) ?: throw UsernameNotFoundException("Username not found")
    }

    fun getByUsername(username: String): User? {
        return repository.findByUsername(username)
    }

    fun getByEmail(email: String): User? {
        return repository.findByEmail(email)
    }

    fun save(user: User): User{
        return repository.save(user)
    }

}