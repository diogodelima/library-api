package com.diogo.library.controller

import com.diogo.library.domain.user.User
import com.diogo.library.domain.user.UserForgotPassword
import com.diogo.library.dto.*
import com.diogo.library.exceptions.EmailAlreadyExistsException
import com.diogo.library.exceptions.TokenExpiredException
import com.diogo.library.exceptions.TokenNotFoundException
import com.diogo.library.exceptions.UsernameAlreadyExistsException
import com.diogo.library.services.JwtService
import com.diogo.library.services.MailService
import com.diogo.library.services.UserForgotPasswordService
import com.diogo.library.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("auth")
class AuthenticationController(

    val authenticationManager: AuthenticationManager,
    val userService: UserService,
    val userForgotPasswordService: UserForgotPasswordService,
    val jwtService: JwtService,
    val mailService: MailService,
    val passwordEncoder: PasswordEncoder

) {

    companion object {

        private const val RESET_PASSWORD_URL = "http://localhost:8080/auth/resetpassword?token={token}"

    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid data: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(data.username, data.password)
        val auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        val user = auth.principal as User
        val token = jwtService.generateToken(user)

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

    @PostMapping("/forgotpassword/{username}")
    fun forgotPassword(@PathVariable username: String): ResponseEntity<ForgotPasswordResponseDto> {

        val user = userService.getByUsername(username) ?: throw UsernameNotFoundException("Username not found")
        val userForgotPassword = userForgotPasswordService.save(
            UserForgotPassword(user = user)
        )

        //send mail with link to reset password

        return ResponseEntity.ok(
            ForgotPasswordResponseDto(
                user = user.id!!,
                expiresAt = userForgotPassword.expirationDate
            )
        )

    }

    @PostMapping("/resetpassword")
    fun resetPassword(@RequestBody @Valid request: ResetPasswordRequestDto): ResponseEntity<String> {

        val userForgotPassword = userForgotPasswordService.getById(request.token) ?: throw TokenNotFoundException()
        val now = Date.from(Instant.now())

        userForgotPasswordService.delete(userForgotPassword.id!!)
        if (userForgotPassword.expirationDate.before(now))
            throw TokenExpiredException()

        val user = userForgotPassword.user
        user.password = passwordEncoder.encode(request.password)

        userService.save(user)
        return ResponseEntity.ok("Password changed successfully")
    }

    @PostMapping("/changepassword")
    fun changePassword(@RequestBody @Valid request: ChangePasswordRequestDto): ResponseEntity<String> {

        val user = SecurityContextHolder.getContext().authentication.principal as User

        user.password = passwordEncoder.encode(request.password)
        userService.save(user)

        return ResponseEntity.ok("Password changed successfully")
    }

}