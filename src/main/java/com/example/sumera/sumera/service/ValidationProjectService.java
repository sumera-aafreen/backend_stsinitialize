package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.repository.ValidationProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ValidationProjectService {
    
    private final ValidationProjectRepository validationProjectRepository;
    private final IdeaService ideaService;
    
    public ValidationProject createValidationProject(ValidationProject validationProject) {
        validationProject.setCreatedDate(LocalDateTime.now());
        validationProject.setLastModified(LocalDateTime.now());
        validationProject.setStatus(ValidationProject.ProjectStatus.PLANNING);
        return validationProjectRepository.save(validationProject);
    }
    
    public Optional<ValidationProject> getValidationProjectById(Long projectId) {
        return validationProjectRepository.findById(projectId);
    }
    
    public List<ValidationProject> getAllValidationProjects() {
        return validationProjectRepository.findAll();
    }
    
    public Optional<ValidationProject> getValidationProjectByIdea(Long ideaId) {
        return validationProjectRepository.findByIdeaIdeaId(ideaId);
    }
    
    public List<ValidationProject> getValidationProjectsByStatus(ValidationProject.ProjectStatus status) {
        return validationProjectRepository.findByStatus(status);
    }
    
    public List<ValidationProject> getValidationProjectsByOwner(Long userId) {
        return validationProjectRepository.findByIdeaOwnerUserId(userId);
    }
    
    public List<ValidationProject> getValidationProjectsByOwnerAndStatus(Long userId, ValidationProject.ProjectStatus status) {
        return validationProjectRepository.findByStatusAndIdeaOwnerUserId(status, userId);
    }
    
    public ValidationProject updateValidationProject(ValidationProject validationProject) {
        validationProject.setLastModified(LocalDateTime.now());
        return validationProjectRepository.save(validationProject);
    }
    
    public ValidationProject updateProjectStatus(Long projectId, ValidationProject.ProjectStatus status) {
        Optional<ValidationProject> projectOpt = validationProjectRepository.findById(projectId);
        if (projectOpt.isPresent()) {
            ValidationProject project = projectOpt.get();
            project.setStatus(status);
            project.setLastModified(LocalDateTime.now());
            return validationProjectRepository.save(project);
        }
        return null;
    }
    
    public ValidationProject updateProjectProgress(Long projectId, String progressMetrics) {
        Optional<ValidationProject> projectOpt = validationProjectRepository.findById(projectId);
        if (projectOpt.isPresent()) {
            ValidationProject project = projectOpt.get();
            project.setProgressMetrics(progressMetrics);
            project.setLastModified(LocalDateTime.now());
            return validationProjectRepository.save(project);
        }
        return null;
    }
    
    public void deleteValidationProject(Long projectId) {
        validationProjectRepository.deleteById(projectId);
    }
    
    public ValidationProject startValidation(Long projectId) {
        return updateProjectStatus(projectId, ValidationProject.ProjectStatus.IN_PROGRESS);
    }
    
    public ValidationProject completeValidation(Long projectId) {
        return updateProjectStatus(projectId, ValidationProject.ProjectStatus.COMPLETED);
    }
    
    public ValidationProject cancelValidation(Long projectId) {
        return updateProjectStatus(projectId, ValidationProject.ProjectStatus.CANCELLED);
    }
}
