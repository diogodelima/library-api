package com.diogo.library.domain.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @get:JvmName(name = "username")
    @Column(unique = true, nullable = false)
    val username: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @get:JvmName(name = "password")
    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val role: Role = Role.USER

): UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.identifier))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    enum class Role(

        val identifier: String

    ) {

        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER")

    }

}