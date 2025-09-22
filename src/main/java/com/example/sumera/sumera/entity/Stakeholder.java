package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stakeholders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stakeholder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stakeholder_id")
    private Long stakeholderId;
    
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "stakeholder_type", nullable = false)
    private StakeholderType stakeholderType;
    
    @Column(name = "relationship")
    private String relationship;
    
    @Column(name = "contact_info")
    private String contactInfo;
    
    @Column(name = "interaction_history", columnDefinition = "TEXT")
    private String interactionHistory;
    
    @Column(name = "engagement_level")
    private Integer engagementLevel = 0;
    
    public enum StakeholderType {
        INVESTOR,
        MENTOR,
        ADVISOR,
        CUSTOMER,
        PARTNER,
        SUPPLIER,
        REGULATOR
    }
}

