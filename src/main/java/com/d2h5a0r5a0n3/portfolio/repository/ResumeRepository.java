package com.d2h5a0r5a0n3.portfolio.repository;

import com.d2h5a0r5a0n3.portfolio.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByIsActiveTrue();
    Optional<Resume> findByFileHash(String fileHash);
}
