package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "expert_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;
    
    @NotNull(message = "Validation project is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ValidationProject validationProject;
    
    @NotNull(message = "Expert is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)
    private User expert;
    
    @Column(name = "feedback_type")
    private String feedbackType;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    
    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "confidentiality_level", nullable = false)
    private ConfidentialityLevel confidentialityLevel = ConfidentialityLevel.PRIVATE;
    
    @CreationTimestamp
    @Column(name = "submission_date", nullable = false, updatable = false)
    private LocalDateTime submissionDate;
    
    public enum ConfidentialityLevel {
        PUBLIC,
        PRIVATE,
        EXPERT_ONLY,
        INVESTOR_ACCESSIBLE
    }
}

