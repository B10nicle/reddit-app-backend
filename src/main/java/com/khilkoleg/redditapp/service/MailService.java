package com.khilkoleg.redditapp.service;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.khilkoleg.redditapp.exceptions.EmailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import com.khilkoleg.redditapp.model.NotificationEmail;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Oleg Khilko
 */

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Async
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("no-reply@reddit-app.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email was sent.");
        } catch (MailException e) {
            log.error("Sending activation email failed.", e);
            throw new EmailException("Exception occurred when sending an activation email to " + notificationEmail.getRecipient(), e);
        }
    }

}
