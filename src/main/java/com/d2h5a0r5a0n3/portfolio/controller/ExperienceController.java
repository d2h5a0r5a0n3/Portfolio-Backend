package com.d2h5a0r5a0n3.portfolio.controller;

import com.d2h5a0r5a0n3.portfolio.model.Experience;
import com.d2h5a0r5a0n3.portfolio.model.JobType;
import com.d2h5a0r5a0n3.portfolio.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<Experience>> getAll() {
        return ResponseEntity.ok(experienceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getById(@PathVariable Long id) {
        return experienceService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Experience> create(@Valid @RequestBody Experience experience) {
        return ResponseEntity.ok(experienceService.create(experience));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> update(@PathVariable Long id, @Valid @RequestBody Experience experience) {
        return experienceService.update(id, experience)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return experienceService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/getJobType")
    public List<JobType> getAllJobType() {
        return Arrays.asList(JobType.values());
    }

}
