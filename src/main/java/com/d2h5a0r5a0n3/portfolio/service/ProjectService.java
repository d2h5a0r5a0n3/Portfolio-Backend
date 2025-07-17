package com.d2h5a0r5a0n3.portfolio.service;

import com.d2h5a0r5a0n3.portfolio.model.Project;
import com.d2h5a0r5a0n3.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository repo;
    @Autowired
    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public List<Project> getAll() {
        return repo.findAll();
    }

    public Project getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Project create(Project project) {
        return repo.save(project);
    }

    public Project update(Long id, Project newProject) {
        return repo.findById(id).map(p -> {
            p.setTitle(newProject.getTitle());
            p.setTechStack(newProject.getTechStack());
            p.setDescription(newProject.getDescription());
            p.setGithub(newProject.getGithub());
            p.setDemo(newProject.getDemo());
            p.setEndDate(newProject.getEndDate());
            p.setStartDate(newProject.getStartDate());
            return repo.save(p);
        }).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

