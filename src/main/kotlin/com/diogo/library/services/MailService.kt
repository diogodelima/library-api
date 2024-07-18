package com.diogo.library.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(

    val mailSender: JavaMailSender

) {

    fun send(email: String, message: String){

        val simpleMessage = SimpleMailMessage()
        simpleMessage.setTo(email)
        simpleMessage.text = message

        mailSender.send(simpleMessage)
    }

}