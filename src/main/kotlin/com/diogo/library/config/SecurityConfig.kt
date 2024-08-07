package com.diogo.library.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(

    val securityFilter: SecurityFilter

) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/forgotpassword/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/resetpassword").permitAll()
                    .requestMatchers(HttpMethod.GET, "/book/list/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/book/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/author/list/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/author/*").permitAll()
                    .requestMatchers(HttpMethod.POST, "/author/create").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/author/update").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/author/delete/*").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/book/create").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/book/update").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/book/delete/*").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}