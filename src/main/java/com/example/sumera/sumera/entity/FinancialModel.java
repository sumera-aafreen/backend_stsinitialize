package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "financial_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long modelId;
    
    @NotNull(message = "Validation project is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ValidationProject validationProject;
    
    @Column(name = "model_type")
    private String modelType;
    
    @Column(name = "assumptions", columnDefinition = "TEXT")
    private String assumptions;
    
    @Column(name = "projections", columnDefinition = "TEXT")
    private String projections;
    
    @Column(name = "scenarios", columnDefinition = "TEXT")
    private String scenarios;
    
    @Column(name = "validation_results", columnDefinition = "TEXT")
    private String validationResults;
    
    @Column(name = "expert_review", columnDefinition = "TEXT")
    private String expertReview;
    
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
}

