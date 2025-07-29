package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.enums.JobType;
import com.d2h5a0r5a0n3.portfolio.enums.Proficiency;
import com.d2h5a0r5a0n3.portfolio.enums.SkillAction;
import com.d2h5a0r5a0n3.portfolio.enums.SkillCategory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enums")
@CrossOrigin
public class EnumController {

    @GetMapping("/proficiencies")
    public Proficiency[] getProficiencies() {
        return Proficiency.values();
    }

    @GetMapping("/skill-categories")
    public SkillCategory[] getSkillCategories() {
        return SkillCategory.values();
    }

    @GetMapping("/skill-actions")
    public SkillAction[] getSkillActions() {
        return SkillAction.values();
    }

    @GetMapping("/job-type")
    public JobType[] getJobTypes(){ return JobType.values(); }
}
