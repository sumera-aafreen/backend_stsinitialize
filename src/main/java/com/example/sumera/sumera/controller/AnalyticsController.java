package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.Analytics;
import com.example.sumera.sumera.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics and Insights", description = "APIs for analytics, insights, and business intelligence")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/project/{id}")
    @Operation(summary = "Get project analytics", description = "Get comprehensive analytics for a validation project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project analytics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<List<Analytics>> getProjectAnalytics(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        List<Analytics> analytics = analyticsService.getProjectInsights(id);
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/success-probability/{id}")
    @Operation(summary = "Get success prediction", description = "Get success probability prediction for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success prediction retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<List<Analytics>> getSuccessProbability(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        List<Analytics> analytics = analyticsService.getSuccessProbabilityMetrics(id);
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/market-opportunity/{id}")
    @Operation(summary = "Get market opportunity analysis", description = "Get market opportunity analysis for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Market opportunity analysis retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<List<Analytics>> getMarketOpportunityAnalysis(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        List<Analytics> analytics = analyticsService.getMarketOpportunityMetrics(id);
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/insights/recommendations/{id}")
    @Operation(summary = "Get actionable recommendations", description = "Get actionable recommendations for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<String> getActionableRecommendations(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        // This would be implemented with a proper recommendations service
        return ResponseEntity.ok("Actionable recommendations for project: " + id);
    }
    
    @GetMapping("/benchmarks")
    @Operation(summary = "Get industry benchmarks", description = "Get industry benchmarks and standards")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Benchmarks retrieved successfully")
    })
    public ResponseEntity<String> getIndustryBenchmarks(
            @Parameter(description = "Industry") @RequestParam(required = false) String industry,
            @Parameter(description = "Metric type") @RequestParam(required = false) String metricType) {
        // This would be implemented with a proper benchmarks service
        return ResponseEntity.ok("Industry benchmarks data");
    }
    
    @PostMapping("/metrics")
    @Operation(summary = "Create analytics metric", description = "Create a new analytics metric for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Metric created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Analytics> createAnalyticsMetric(
            @RequestParam Long projectId,
            @RequestParam String metricType,
            @RequestParam BigDecimal value,
            @RequestParam String dataSource,
            @RequestParam BigDecimal confidence,
            @RequestParam String trendDirection) {
        
        Analytics analytics = analyticsService.createMetric(
            projectId, metricType, value, dataSource, confidence, trendDirection);
        
        return analytics != null ? 
            ResponseEntity.status(HttpStatus.CREATED).body(analytics) : 
            ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/metrics")
    @Operation(summary = "Get analytics metrics", description = "Get analytics metrics with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metrics retrieved successfully")
    })
    public ResponseEntity<List<Analytics>> getAnalyticsMetrics(
            @Parameter(description = "Project ID") @RequestParam(required = false) Long projectId,
            @Parameter(description = "Metric type") @RequestParam(required = false) String metricType,
            @Parameter(description = "Data source") @RequestParam(required = false) String dataSource) {
        
        List<Analytics> analytics;
        if (projectId != null) {
            analytics = analyticsService.getAnalyticsByProject(projectId);
        } else if (metricType != null) {
            analytics = analyticsService.getAnalyticsByMetricType(metricType);
        } else if (dataSource != null) {
            analytics = analyticsService.getAnalyticsByDataSource(dataSource);
        } else {
            analytics = analyticsService.getAllAnalytics();
        }
        
        return ResponseEntity.ok(analytics);
    }
    
    @GetMapping("/metrics/{id}")
    @Operation(summary = "Get analytics metric by ID", description = "Get a specific analytics metric by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Metric retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Metric not found")
    })
    public ResponseEntity<Analytics> getAnalyticsMetric(
            @Parameter(description = "Analytics ID") @PathVariable Long id) {
        Optional<Analytics> analytics = analyticsService.getAnalyticsById(id);
        return analytics.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}

