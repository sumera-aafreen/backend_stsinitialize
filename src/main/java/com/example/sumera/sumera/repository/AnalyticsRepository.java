package com.example.sumera.sumera.repository;

import com.example.sumera.sumera.entity.Analytics;
import com.example.sumera.sumera.entity.ValidationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    
    List<Analytics> findByValidationProject(ValidationProject validationProject);
    
    List<Analytics> findByValidationProjectProjectId(Long projectId);
    
    List<Analytics> findByMetricType(String metricType);
    
    List<Analytics> findByDataSource(String dataSource);
    
    @Query("SELECT a FROM Analytics a WHERE a.value >= :minValue")
    List<Analytics> findByValueGreaterThanEqual(@Param("minValue") BigDecimal minValue);
    
    @Query("SELECT a FROM Analytics a WHERE a.confidence >= :minConfidence")
    List<Analytics> findByConfidenceGreaterThanEqual(@Param("minConfidence") BigDecimal minConfidence);
    
    @Query("SELECT a FROM Analytics a WHERE a.validationProject.idea.owner.userId = :userId")
    List<Analytics> findByValidationProjectIdeaOwnerUserId(@Param("userId") Long userId);
}

