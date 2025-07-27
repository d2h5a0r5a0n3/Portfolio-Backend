package com.d2h5a0r5a0n3.portfolio.resume;

import com.d2h5a0r5a0n3.portfolio.model.Resume;
import com.d2h5a0r5a0n3.portfolio.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ResumeRepository resumeRepository;

    private String computeFileHash(MultipartFile file) {
        try (var inputStream = file.getInputStream()) {
            var digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] bytes = inputStream.readAllBytes();
            byte[] hash = digest.digest(bytes);
            return java.util.HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute hash", e);
        }
    }

    @SneakyThrows
    public Resume storeFile(MultipartFile file) {
        String fileHash = computeFileHash(file);
        if (resumeRepository.findByFileHash(fileHash).isPresent()) {
            throw new RuntimeException("Duplicate file upload not allowed.");
        }
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        resumeRepository.findByIsActiveTrue().ifPresent(r -> {
            r.setActive(false);
            resumeRepository.save(r);
        });
        Resume resume = new Resume();
        resume.setFilename(filename);
        resume.setOriginalName(file.getOriginalFilename());
        resume.setUploadTime(LocalDateTime.now());
        resume.setActive(true);
        resume.setFileHash(fileHash);
        return resumeRepository.save(resume);
    }

    public Resource loadFile(String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Resume> listAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume getActiveResume() {
        return resumeRepository.findByIsActiveTrue().orElse(null);
    }

    public Resume setActiveResume(Long id) {
        resumeRepository.findAll().forEach(resume -> {
            resume.setActive(false);
            resumeRepository.save(resume);
        });
        Resume active = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        active.setActive(true);
        return resumeRepository.save(active);
    }
    public void deleteResume(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        Path filePath = Paths.get(uploadDir).resolve(resume.getFilename());
        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + resume.getFilename());
        }
        resumeRepository.delete(resume);
        if (resume.isActive()) {
            resumeRepository.findAll().stream()
                    .max((r1, r2) -> r1.getUploadTime().compareTo(r2.getUploadTime()))
                    .ifPresent(latest -> {
                        latest.setActive(true);
                        resumeRepository.save(latest);
                    });
        }
    }
}
