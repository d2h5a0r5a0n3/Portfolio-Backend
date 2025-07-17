package com.d2h5a0r5a0n3.portfolio.repository;

import com.d2h5a0r5a0n3.portfolio.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {
}
