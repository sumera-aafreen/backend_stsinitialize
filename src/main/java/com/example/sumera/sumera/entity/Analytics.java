package com.example.sumera.sumera.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Analytics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Long analyticsId;
    
    @NotNull(message = "Validation project is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ValidationProject validationProject;
    
    @Column(name = "metric_type")
    private String metricType;
    
    @Column(name = "value", precision = 10, scale = 2)
    private BigDecimal value;
    
    @CreationTimestamp
    @Column(name = "calculation_date", nullable = false, updatable = false)
    private LocalDateTime calculationDate;
    
    @Column(name = "data_source")
    private String dataSource;
    
    @Column(name = "confidence", precision = 3, scale = 2)
    private BigDecimal confidence;
    
    @Column(name = "trend_direction")
    private String trendDirection;
}

