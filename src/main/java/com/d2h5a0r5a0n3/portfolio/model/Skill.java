package com.d2h5a0r5a0n3.portfolio.model;

import com.d2h5a0r5a0n3.portfolio.enums.Proficiency;
import com.d2h5a0r5a0n3.portfolio.enums.SkillCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private SkillCategory skillCategory;
    @Enumerated(EnumType.STRING)
    private Proficiency proficiency;
    private String iconUrl;
    private String description;
    @OneToMany(mappedBy = "skill",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SkillLog> logs;
}
