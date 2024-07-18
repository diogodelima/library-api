package com.diogo.library.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig(

    @Value("\${api.mail.host}")
    val host: String,

    @Value("\${api.mail.port}")
    val port: Int,

    @Value("\${api.mail.protocol}")
    val protocol: String,

    @Value("\${api.mail.username}")
    val username: String,

    @Value("\${api.mail.password}")
    val password: String,

    @Value("\${api.mail.smtp.auth}")
    val smtpAuth: Boolean,

    @Value("\${api.mail.smtp.starttls.enable}")
    val startTls: Boolean,

    @Value("\${api.mail.smtp.ssl.enable}")
    val ssl: Boolean,

    @Value("\${api.mail.debug}")
    val debug: Boolean

) {

    @Bean
    fun mailSender(): JavaMailSender {

        val mailSender = JavaMailSenderImpl()

        mailSender.host = host
        mailSender.port = port
        mailSender.protocol = protocol
        mailSender.username = username
        mailSender.password = password

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = protocol
        props["mail.smtp.auth"] = smtpAuth
        props["mail.smtp.starttls.enable"] = startTls
        props["mail.smtp.starttls.required"] = startTls
        props["mail.smtp.ssl.enable"] = ssl
        props["mail.debug"] = debug

        return mailSender
    }

}