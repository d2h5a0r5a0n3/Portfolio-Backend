package com.d2h5a0r5a0n3.portfolio.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactForm {
    private String name;
    private String mail;
    private String subject;
    private String message;
}
