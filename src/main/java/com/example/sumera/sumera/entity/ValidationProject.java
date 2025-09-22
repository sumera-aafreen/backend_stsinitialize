package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "validation_projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationProject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;
    
    @NotNull(message = "Idea is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id", nullable = false, unique = true)
    private Idea idea;
    
    @Column(name = "validation_plan", columnDefinition = "TEXT")
    private String validationPlan;
    
    @Column(name = "milestones", columnDefinition = "TEXT")
    private String milestones;
    
    @Column(name = "timeline")
    private LocalDate timeline;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status = ProjectStatus.PLANNING;
    
    @Column(name = "budget", precision = 10, scale = 2)
    private BigDecimal budget;
    
    @Column(name = "progress_metrics", columnDefinition = "TEXT")
    private String progressMetrics;
    
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    
    // Relationships
    @OneToMany(mappedBy = "validationProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpertFeedback> expertFeedbacks;
    
    @OneToMany(mappedBy = "validationProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommunityValidation> communityValidations;
    
    @OneToMany(mappedBy = "validationProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FinancialModel> financialModels;
    
    @OneToMany(mappedBy = "validationProject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Analytics> analytics;
    
    public enum ProjectStatus {
        PLANNING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED,
        ON_HOLD
    }
}
