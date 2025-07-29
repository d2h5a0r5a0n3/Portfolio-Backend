package com.d2h5a0r5a0n3.portfolio.model;

import com.d2h5a0r5a0n3.portfolio.enums.SkillAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillAction action;
    private String notes;
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id",nullable = false)
    private Skill skill;

    @PrePersist
    public void setTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}
