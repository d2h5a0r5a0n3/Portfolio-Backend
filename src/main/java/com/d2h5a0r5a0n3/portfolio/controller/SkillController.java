package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.dto.SkillDTO;
import com.d2h5a0r5a0n3.portfolio.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@CrossOrigin // Optional: needed if frontend is on a different port
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public List<SkillDTO> getAllSkills() {
        return skillService.getAllSkills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable Long id) {
        SkillDTO skill = skillService.getSkillById(id);
        return skill != null ? ResponseEntity.ok(skill) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO dto) {
        return ResponseEntity.ok(skillService.createSkill(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @RequestBody SkillDTO dto) {
        SkillDTO updated = skillService.updateSkill(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
