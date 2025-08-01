package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.enums.JobType;
import com.d2h5a0r5a0n3.portfolio.enums.Proficiency;
import com.d2h5a0r5a0n3.portfolio.enums.SkillAction;
import com.d2h5a0r5a0n3.portfolio.enums.SkillCategory;
import com.d2h5a0r5a0n3.portfolio.util.EnumUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enums")
@CrossOrigin
@RequiredArgsConstructor
public class EnumController {

    private final EnumUtils enumUtils;

    @GetMapping("/proficiencies")
    public List<String> getProficiencies() {
        return enumUtils.getFormattedEnumValues(Proficiency.class);
    }

    @GetMapping("/skill-categories")
    public List<String> getSkillCategories() {
        return enumUtils.getFormattedEnumValues(SkillCategory.class);
    }

    @GetMapping("/skill-actions")
    public List<String> getSkillActions() {
        return enumUtils.getFormattedEnumValues(SkillAction.class);
    }

    @GetMapping("/job-types")
    public List<String> getJobTypes() {
        return enumUtils.getFormattedEnumValues(JobType.class);
    }
}
