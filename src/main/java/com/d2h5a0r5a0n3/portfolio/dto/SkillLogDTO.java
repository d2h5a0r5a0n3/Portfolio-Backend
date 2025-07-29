package com.d2h5a0r5a0n3.portfolio.dto;

import com.d2h5a0r5a0n3.portfolio.enums.SkillAction;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillLogDTO {
    private Long id;
    private SkillAction action;
    private String notes;
    private LocalDateTime timestamp;
    private Long skillId;
}
