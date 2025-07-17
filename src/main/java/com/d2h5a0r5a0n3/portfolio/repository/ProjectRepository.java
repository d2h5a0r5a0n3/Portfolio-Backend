package com.d2h5a0r5a0n3.portfolio.repository;

import com.d2h5a0r5a0n3.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
