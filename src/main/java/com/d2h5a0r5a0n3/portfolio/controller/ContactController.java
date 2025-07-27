package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.dto.ContactForm;
import com.d2h5a0r5a0n3.portfolio.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final MailService mailService;
    @Autowired
    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMail(@RequestBody ContactForm form) {
        try {
            mailService.sendContactMessage(form);
            Map<String, String> response = Map.of("message", "Message sent successfully");
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send message"));
        }
    }

}