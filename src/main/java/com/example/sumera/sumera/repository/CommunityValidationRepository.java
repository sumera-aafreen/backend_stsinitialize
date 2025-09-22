package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.CommunityValidation;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.entity.ValidationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityValidationRepository extends JpaRepository<CommunityValidation, Long> {
    
    List<CommunityValidation> findByValidationProject(ValidationProject validationProject);
    
    List<CommunityValidation> findByValidationProjectProjectId(Long projectId);
    
    List<CommunityValidation> findByValidator(User validator);
    
    List<CommunityValidation> findByValidatorUserId(Long validatorId);
    
    List<CommunityValidation> findByValidationType(String validationType);
    
    @Query("SELECT cv FROM CommunityValidation cv WHERE cv.rating >= :minRating")
    List<CommunityValidation> findByRatingGreaterThanEqual(@Param("minRating") Integer minRating);
    
    @Query("SELECT cv FROM CommunityValidation cv WHERE cv.validationProject.projectId = :projectId AND cv.validator.userId = :validatorId")
    List<CommunityValidation> findByProjectIdAndValidatorId(@Param("projectId") Long projectId, @Param("validatorId") Long validatorId);
}

