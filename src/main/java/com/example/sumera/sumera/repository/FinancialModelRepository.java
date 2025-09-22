package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.FinancialModel;
import com.example.sumera.sumera.entity.ValidationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialModelRepository extends JpaRepository<FinancialModel, Long> {
    
    List<FinancialModel> findByValidationProject(ValidationProject validationProject);
    
    List<FinancialModel> findByValidationProjectProjectId(Long projectId);
    
    List<FinancialModel> findByModelType(String modelType);
    
    @Query("SELECT fm FROM FinancialModel fm WHERE fm.validationProject.idea.owner.userId = :userId")
    List<FinancialModel> findByValidationProjectIdeaOwnerUserId(@Param("userId") Long userId);
}

