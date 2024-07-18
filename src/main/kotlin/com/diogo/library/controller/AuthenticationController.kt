package com.diogo.library.controller

import com.diogo.library.domain.user.User
import com.diogo.library.dto.LoginRequestDto
import com.diogo.library.dto.LoginResponseDto
import com.diogo.library.dto.RegisterRequestDto
import com.diogo.library.exceptions.EmailAlreadyExistsException
import com.diogo.library.exceptions.UsernameAlreadyExistsException
import com.diogo.library.services.JwtService
import com.diogo.library.services.MailService
import com.diogo.library.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthenticationController(

    val authenticationManager: AuthenticationManager,
    val userService: UserService,
    val jwtService: JwtService,
    val mailService: MailService,
    val passwordEncoder: PasswordEncoder

) {

    @PostMapping("/login")
    fun login(@RequestBody @Valid data: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(data.username, data.password)
        val auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        val token = jwtService.generateToken(auth.principal as User)

        return ResponseEntity.ok(
            LoginResponseDto(token)
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody @Valid data: RegisterRequestDto): ResponseEntity<Any> {

        if (userService.getByUsername(data.username) != null)
            throw UsernameAlreadyExistsException()

        if (userService.getByEmail(data.email) != null)
            throw EmailAlreadyExistsException()

        val encryptedPassword = passwordEncoder.encode(data.password)
        val user = User(
            email = data.email,
            username = data.username,
            password = encryptedPassword
        )

        userService.save(user)

        return ResponseEntity.ok().build()
    }

}