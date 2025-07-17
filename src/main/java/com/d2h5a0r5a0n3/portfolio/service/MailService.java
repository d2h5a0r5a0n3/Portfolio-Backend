package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.dto.ContactForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String toMail;

    public void sendContactMessage(ContactForm form) throws MessagingException {
        MimeMessage mime=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mime,true);
        helper.setFrom(toMail);
        helper.setTo(toMail);
        helper.setSubject("Portfolio-App : New Contact Message from "+form.getName());
        helper.setText(
                "You have received a new message from your portfolio site:\n\n" +
                        "Name: " + form.getName() + "\n" +
                        "Email: " + form.getMail() + "\n" +
                        "Subject: "+form.getSubject()+"\n\n"+
                        "Message:\n" + form.getMessage()
        );
        helper.setReplyTo(form.getMail());
        mailSender.send(mime);
    }
}
