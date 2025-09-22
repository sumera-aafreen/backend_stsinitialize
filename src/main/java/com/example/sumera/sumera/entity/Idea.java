package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ideas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Idea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long ideaId;
    
    @NotNull(message = "Owner is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "problem_statement", columnDefinition = "TEXT")
    private String problemStatement;
    
    @Column(name = "solution", columnDefinition = "TEXT")
    private String solution;
    
    @Column(name = "target_market")
    private String targetMarket;
    
    @Column(name = "business_model")
    private String businessModel;
    
    @Column(name = "stage")
    private String stage;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "confidentiality_level", nullable = false)
    private ConfidentialityLevel confidentialityLevel = ConfidentialityLevel.PRIVATE;
    
    @CreationTimestamp
    @Column(name = "submission_date", nullable = false, updatable = false)
    private LocalDateTime submissionDate;
    
    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    
    // Relationships
    @OneToOne(mappedBy = "idea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ValidationProject validationProject;
    
    public enum ConfidentialityLevel {
        PUBLIC,
        PRIVATE,
        EXPERT_ONLY,
        INVESTOR_ACCESSIBLE
    }
}
