package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @NotBlank(message = "Username is required")
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    @Column(name = "role", nullable = false)
    private UserRole role;
    
    @Column(name = "expertise")
    private String expertise;
    
    @Column(name = "verification_status", nullable = false)
    private Boolean verificationStatus = false;
    
    @Column(name = "profile_data", columnDefinition = "TEXT")
    private String profileData;
    
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @UpdateTimestamp
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    // Relationships
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Idea> ideas;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stakeholder> stakeholders;
    
    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExpertFeedback> expertFeedbacks;
    
    @OneToMany(mappedBy = "validator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommunityValidation> communityValidations;
    
    public enum UserRole {
        ENTREPRENEUR,
        INVESTOR,
        MENTOR,
        EXPERT,
        VALIDATOR,
        ANALYST,
        ADMIN
    }
}

