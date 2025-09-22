package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.CommunityValidation;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.repository.CommunityValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityValidationService {
    
    private final CommunityValidationRepository communityValidationRepository;
    private final UserService userService;
    private final ValidationProjectService validationProjectService;
    
    public CommunityValidation createCommunityValidation(CommunityValidation communityValidation) {
        communityValidation.setParticipationDate(LocalDateTime.now());
        return communityValidationRepository.save(communityValidation);
    }
    
    public Optional<CommunityValidation> getCommunityValidationById(Long validationId) {
        return communityValidationRepository.findById(validationId);
    }
    
    public List<CommunityValidation> getAllCommunityValidations() {
        return communityValidationRepository.findAll();
    }
    
    public List<CommunityValidation> getCommunityValidationsByProject(Long projectId) {
        return communityValidationRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<CommunityValidation> getCommunityValidationsByValidator(Long validatorId) {
        return communityValidationRepository.findByValidatorUserId(validatorId);
    }
    
    public List<CommunityValidation> getCommunityValidationsByType(String validationType) {
        return communityValidationRepository.findByValidationType(validationType);
    }
    
    public List<CommunityValidation> getHighRatedValidations(Integer minRating) {
        return communityValidationRepository.findByRatingGreaterThanEqual(minRating);
    }
    
    public List<CommunityValidation> getCommunityValidationsByProjectAndValidator(Long projectId, Long validatorId) {
        return communityValidationRepository.findByProjectIdAndValidatorId(projectId, validatorId);
    }
    
    public CommunityValidation updateCommunityValidation(CommunityValidation communityValidation) {
        return communityValidationRepository.save(communityValidation);
    }
    
    public void deleteCommunityValidation(Long validationId) {
        communityValidationRepository.deleteById(validationId);
    }
    
    public CommunityValidation submitValidation(Long projectId, Long validatorId, String validationType,
                                              String responses, Integer rating, String feedback, String demographics) {
        Optional<ValidationProject> projectOpt = validationProjectService.getValidationProjectById(projectId);
        Optional<User> validatorOpt = userService.getUserById(validatorId);
        
        if (projectOpt.isPresent() && validatorOpt.isPresent()) {
            CommunityValidation validation = new CommunityValidation();
            validation.setValidationProject(projectOpt.get());
            validation.setValidator(validatorOpt.get());
            validation.setValidationType(validationType);
            validation.setResponses(responses);
            validation.setRating(rating);
            validation.setFeedback(feedback);
            validation.setDemographics(demographics);
            
            return createCommunityValidation(validation);
        }
        return null;
    }
}

