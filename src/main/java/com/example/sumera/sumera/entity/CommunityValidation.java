package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_validation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityValidation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "validation_id")
    private Long validationId;
    
    @NotNull(message = "Validation project is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ValidationProject validationProject;
    
    @NotNull(message = "Validator is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validator_id", nullable = false)
    private User validator;
    
    @Column(name = "validation_type")
    private String validationType;
    
    @Column(name = "responses", columnDefinition = "TEXT")
    private String responses;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;
    
    @Column(name = "demographics", columnDefinition = "TEXT")
    private String demographics;
    
    @CreationTimestamp
    @Column(name = "participation_date", nullable = false, updatable = false)
    private LocalDateTime participationDate;
}

