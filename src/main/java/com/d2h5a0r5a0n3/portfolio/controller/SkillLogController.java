package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.dto.SkillLogDTO;
import com.d2h5a0r5a0n3.portfolio.service.SkillLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills/{skillId}/logs")
@RequiredArgsConstructor
@CrossOrigin
public class SkillLogController {

    private final SkillLogService skillLogService;

    @GetMapping
    public List<SkillLogDTO> getLogs(@PathVariable Long skillId) {
        return skillLogService.getLogsBySkillId(skillId);
    }

    @PostMapping
    public ResponseEntity<SkillLogDTO> addLog(@PathVariable Long skillId, @RequestBody SkillLogDTO dto) {
        dto.setSkillId(skillId);
        SkillLogDTO saved = skillLogService.createLog(dto);
        return saved != null ? ResponseEntity.ok(saved) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long logId) {
        skillLogService.deleteLog(logId);
        return ResponseEntity.noContent().build();
    }
}
