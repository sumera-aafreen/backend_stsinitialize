package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.ExpertFeedback;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.entity.ValidationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertFeedbackRepository extends JpaRepository<ExpertFeedback, Long> {
    
    List<ExpertFeedback> findByValidationProject(ValidationProject validationProject);
    
    List<ExpertFeedback> findByValidationProjectProjectId(Long projectId);
    
    List<ExpertFeedback> findByExpert(User expert);
    
    List<ExpertFeedback> findByExpertUserId(Long expertId);
    
    List<ExpertFeedback> findByFeedbackType(String feedbackType);
    
    List<ExpertFeedback> findByConfidentialityLevel(ExpertFeedback.ConfidentialityLevel confidentialityLevel);
    
    @Query("SELECT ef FROM ExpertFeedback ef WHERE ef.rating >= :minRating")
    List<ExpertFeedback> findByRatingGreaterThanEqual(@Param("minRating") Integer minRating);
    
    @Query("SELECT ef FROM ExpertFeedback ef WHERE ef.validationProject.projectId = :projectId AND ef.expert.userId = :expertId")
    List<ExpertFeedback> findByProjectIdAndExpertId(@Param("projectId") Long projectId, @Param("expertId") Long expertId);
}

