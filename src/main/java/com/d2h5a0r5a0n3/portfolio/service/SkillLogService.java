package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.dto.SkillLogDTO;
import com.d2h5a0r5a0n3.portfolio.model.Skill;
import com.d2h5a0r5a0n3.portfolio.model.SkillLog;
import com.d2h5a0r5a0n3.portfolio.repository.SkillLogRepository;
import com.d2h5a0r5a0n3.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillLogService {

    private final SkillLogRepository logRepository;
    private final SkillRepository skillRepository;

    public List<SkillLogDTO> getLogsBySkillId(Long skillId) {
        return logRepository.findBySkillId(skillId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SkillLogDTO createLog(SkillLogDTO dto) {
        Optional<Skill> skillOpt = skillRepository.findById(dto.getSkillId());
        if (skillOpt.isEmpty()) return null;

        SkillLog log = new SkillLog();
        log.setSkill(skillOpt.get());
        log.setAction(dto.getAction());
        log.setNotes(dto.getNotes());

        return toDTO(logRepository.save(log));
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }

    private SkillLogDTO toDTO(SkillLog log) {
        return new SkillLogDTO(
                log.getId(),
                log.getAction(),
                log.getNotes(),
                log.getTimestamp(),
                log.getSkill().getId()
        );
    }
}
