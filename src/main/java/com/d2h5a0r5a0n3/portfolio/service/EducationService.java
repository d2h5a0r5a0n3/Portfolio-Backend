package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.model.Education;
import com.d2h5a0r5a0n3.portfolio.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    public Optional<Education> getEducationById(Long id) {
        return educationRepository.findById(id);
    }

    public Education createEducation(Education education) {
        return educationRepository.save(education);
    }

    public Education updateEducation(Long id, Education updatedEducation) {
        return educationRepository.findById(id)
                .map(edu -> {
                    edu.setInstitution(updatedEducation.getInstitution());
                    edu.setDegree(updatedEducation.getDegree());
                    edu.setFieldOfStudy(updatedEducation.getFieldOfStudy());
                    edu.setStartDate(updatedEducation.getStartDate());
                    edu.setEndDate(updatedEducation.getEndDate());
                    edu.setDescription(updatedEducation.getDescription());
                    return educationRepository.save(edu);
                })
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }
}
