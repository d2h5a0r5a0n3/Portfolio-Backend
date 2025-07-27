package com.d2h5a0r5a0n3.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String techStack;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String github;
    private String demo;
}

