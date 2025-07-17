package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.model.Experience;
import com.d2h5a0r5a0n3.portfolio.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository repository;

    public List<Experience> getAll() {
        return repository.findAll();
    }

    public Optional<Experience> getById(Long id) {
        return repository.findById(id);
    }

    public Experience create(Experience experience) {
        return repository.save(experience);
    }

    public Optional<Experience> update(Long id, Experience updatedExperience) {
        return repository.findById(id).map(existing -> {
            existing.setRole(updatedExperience.getRole());
            existing.setCompany(updatedExperience.getCompany());
            existing.setJobType(updatedExperience.getJobType());
            existing.setStartDate(updatedExperience.getStartDate());
            existing.setEndDate(updatedExperience.getEndDate());
            existing.setLocation(updatedExperience.getLocation());
            existing.setDescription(updatedExperience.getDescription());
            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        Optional<Experience> experience = repository.findById(id);
        if (experience.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
