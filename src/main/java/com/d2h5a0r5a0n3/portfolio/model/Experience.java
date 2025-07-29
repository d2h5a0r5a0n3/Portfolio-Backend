package com.d2h5a0r5a0n3.portfolio.model;

import com.d2h5a0r5a0n3.portfolio.enums.JobType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String role;
    private String company;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String location;
}
