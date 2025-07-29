package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.dto.SkillDTO;
import com.d2h5a0r5a0n3.portfolio.model.Skill;
import com.d2h5a0r5a0n3.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<SkillDTO> getAllSkills() {
        return skillRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SkillDTO getSkillById(Long id) {
        return skillRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public SkillDTO createSkill(SkillDTO dto) {
        Skill skill = toEntity(dto);
        return toDTO(skillRepository.save(skill));
    }

    public SkillDTO updateSkill(Long id, SkillDTO dto) {
        return skillRepository.findById(id).map(skill -> {
            skill.setName(dto.getName());
            skill.setSkillCategory(dto.getSkillCategory());
            skill.setProficiency(dto.getProficiency());
            skill.setDescription(dto.getDescription());
            skill.setIconUrl(dto.getIconUrl());
            return toDTO(skillRepository.save(skill));
        }).orElse(null);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
    private SkillDTO toDTO(Skill skill) {
        return new SkillDTO(
                skill.getId(),
                skill.getName(),
                skill.getSkillCategory(),
                skill.getProficiency(),
                skill.getDescription(),
                skill.getIconUrl()
        );
    }
    private Skill toEntity(SkillDTO dto) {
        Skill skill = new Skill();
        skill.setId(dto.getId());
        skill.setName(dto.getName());
        skill.setSkillCategory(dto.getSkillCategory());
        skill.setProficiency(dto.getProficiency());
        skill.setDescription(dto.getDescription());
        skill.setIconUrl(dto.getIconUrl());
        return skill;
    }
}
