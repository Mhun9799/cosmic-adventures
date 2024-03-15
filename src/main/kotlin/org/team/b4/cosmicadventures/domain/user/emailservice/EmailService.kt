package org.team.b4.cosmicadventures.domain.user.emailservice

import jakarta.mail.MessagingException


interface EmailService {
    @Throws(MessagingException::class)
    fun sendHtmlMessage(to: String, subject: String, htmlContent: String)

    @Throws(MessagingException::class)
    fun sendVerificationEmail(to: String, verificationCode: String)

    @Throws(MessagingException::class)
    fun sendVerificationEmail(to: String, verificationCode: String, passwordChars: String)
}
