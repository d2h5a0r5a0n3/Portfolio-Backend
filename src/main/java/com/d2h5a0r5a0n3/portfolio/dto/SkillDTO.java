package com.d2h5a0r5a0n3.portfolio.dto;

import com.d2h5a0r5a0n3.portfolio.enums.Proficiency;
import com.d2h5a0r5a0n3.portfolio.enums.SkillCategory;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private Long id;
    private String name;
    private SkillCategory skillCategory;
    private Proficiency proficiency;
    private String description;
    private String iconUrl;
}
