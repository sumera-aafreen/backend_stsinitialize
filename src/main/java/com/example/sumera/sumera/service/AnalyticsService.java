package com.example.sumera.sumera.service;

import com.example.sumera.sumera.entity.Analytics;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticsService {
    
    private final AnalyticsRepository analyticsRepository;
    private final ValidationProjectService validationProjectService;
    
    public Analytics createAnalytics(Analytics analytics) {
        analytics.setCalculationDate(LocalDateTime.now());
        return analyticsRepository.save(analytics);
    }
    
    public Optional<Analytics> getAnalyticsById(Long analyticsId) {
        return analyticsRepository.findById(analyticsId);
    }
    
    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }
    
    public List<Analytics> getAnalyticsByProject(Long projectId) {
        return analyticsRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<Analytics> getAnalyticsByMetricType(String metricType) {
        return analyticsRepository.findByMetricType(metricType);
    }
    
    public List<Analytics> getAnalyticsByDataSource(String dataSource) {
        return analyticsRepository.findByDataSource(dataSource);
    }
    
    public List<Analytics> getAnalyticsByValueRange(BigDecimal minValue) {
        return analyticsRepository.findByValueGreaterThanEqual(minValue);
    }
    
    public List<Analytics> getAnalyticsByConfidence(BigDecimal minConfidence) {
        return analyticsRepository.findByConfidenceGreaterThanEqual(minConfidence);
    }
    
    public List<Analytics> getAnalyticsByOwner(Long userId) {
        return analyticsRepository.findByValidationProjectIdeaOwnerUserId(userId);
    }
    
    public Analytics updateAnalytics(Analytics analytics) {
        return analyticsRepository.save(analytics);
    }
    
    public void deleteAnalytics(Long analyticsId) {
        analyticsRepository.deleteById(analyticsId);
    }
    
    public Analytics createMetric(Long projectId, String metricType, BigDecimal value,
                                String dataSource, BigDecimal confidence, String trendDirection) {
        Optional<ValidationProject> projectOpt = validationProjectService.getValidationProjectById(projectId);
        
        if (projectOpt.isPresent()) {
            Analytics analytics = new Analytics();
            analytics.setValidationProject(projectOpt.get());
            analytics.setMetricType(metricType);
            analytics.setValue(value);
            analytics.setDataSource(dataSource);
            analytics.setConfidence(confidence);
            analytics.setTrendDirection(trendDirection);
            
            return createAnalytics(analytics);
        }
        return null;
    }
    
    public List<Analytics> getProjectInsights(Long projectId) {
        return analyticsRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<Analytics> getSuccessProbabilityMetrics(Long projectId) {
        return analyticsRepository.findByValidationProjectProjectId(projectId);
    }
    
    public List<Analytics> getMarketOpportunityMetrics(Long projectId) {
        return analyticsRepository.findByValidationProjectProjectId(projectId);
    }
}

