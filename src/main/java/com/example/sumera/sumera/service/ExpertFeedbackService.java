package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.ExpertFeedback;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.repository.ExpertFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertFeedbackService {
    
    private final ExpertFeedbackRepository expertFeedbackRepository;
    private final UserService userService;
    private final ValidationProjectService validationProjectService;
    
    public ExpertFeedback createExpertFeedback(ExpertFeedback expertFeedback) {
        expertFeedback.setSubmissionDate(LocalDateTime.now());
        return expertFeedbackRepository.save(expertFeedback);
    }
    
    public Optional<ExpertFeedback> getExpertFeedbackById(Long feedbackId) {
        return expertFeedbackRepository.findById(feedbackId);
    }
    
    public List<ExpertFeedback> getAllExpertFeedbacks() {
        return expertFeedbackRepository.findAll();
    }
    
    public List<ExpertFeedback> getExpertFeedbacksByProject(Long projectId) {
        return expertFeedbackRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<ExpertFeedback> getExpertFeedbacksByExpert(Long expertId) {
        return expertFeedbackRepository.findByExpertUserId(expertId);
    }
    
    public List<ExpertFeedback> getExpertFeedbacksByType(String feedbackType) {
        return expertFeedbackRepository.findByFeedbackType(feedbackType);
    }
    
    public List<ExpertFeedback> getExpertFeedbacksByConfidentialityLevel(ExpertFeedback.ConfidentialityLevel confidentialityLevel) {
        return expertFeedbackRepository.findByConfidentialityLevel(confidentialityLevel);
    }
    
    public List<ExpertFeedback> getHighRatedFeedbacks(Integer minRating) {
        return expertFeedbackRepository.findByRatingGreaterThanEqual(minRating);
    }
    
    public List<ExpertFeedback> getExpertFeedbacksByProjectAndExpert(Long projectId, Long expertId) {
        return expertFeedbackRepository.findByProjectIdAndExpertId(projectId, expertId);
    }
    
    public ExpertFeedback updateExpertFeedback(ExpertFeedback expertFeedback) {
        return expertFeedbackRepository.save(expertFeedback);
    }
    
    public void deleteExpertFeedback(Long feedbackId) {
        expertFeedbackRepository.deleteById(feedbackId);
    }
    
    public ExpertFeedback submitFeedback(Long projectId, Long expertId, String feedbackType, 
                                       Integer rating, String comments, String recommendations) {
        Optional<ValidationProject> projectOpt = validationProjectService.getValidationProjectById(projectId);
        Optional<User> expertOpt = userService.getUserById(expertId);
        
        if (projectOpt.isPresent() && expertOpt.isPresent()) {
            ExpertFeedback feedback = new ExpertFeedback();
            feedback.setValidationProject(projectOpt.get());
            feedback.setExpert(expertOpt.get());
            feedback.setFeedbackType(feedbackType);
            feedback.setRating(rating);
            feedback.setComments(comments);
            feedback.setRecommendations(recommendations);
            feedback.setConfidentialityLevel(ExpertFeedback.ConfidentialityLevel.PRIVATE);
            
            return createExpertFeedback(feedback);
        }
        return null;
    }
}

