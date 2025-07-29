package com.d2h5a0r5a0n3.portfolio.repository;

import com.d2h5a0r5a0n3.portfolio.model.SkillLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillLogRepository extends JpaRepository<SkillLog, Long> {
    List<SkillLog> findBySkillId(Long skillId);
}
