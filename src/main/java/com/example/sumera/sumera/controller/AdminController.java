package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Operations", description = "Administrative operations for data management")
public class AdminController {
    
    private final UserService userService;
    private final IdeaService ideaService;
    private final ValidationProjectService validationProjectService;
    private final ExpertFeedbackService expertFeedbackService;
    private final CommunityValidationService communityValidationService;
    private final FinancialModelService financialModelService;
    private final AnalyticsService analyticsService;
    
    @DeleteMapping("/clear-all-data")
    @Operation(summary = "Clear all data", description = "Clear all data from the database (WARNING: This will delete all data)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All data cleared successfully"),
        @ApiResponse(responseCode = "500", description = "Error clearing data")
    })
    public ResponseEntity<String> clearAllData() {
        try {
            // Clear all data in reverse order of dependencies
            analyticsService.getAllAnalytics().forEach(analytics -> 
                analyticsService.deleteAnalytics(analytics.getAnalyticsId()));
            
            financialModelService.getAllFinancialModels().forEach(model -> 
                financialModelService.deleteFinancialModel(model.getModelId()));
            
            communityValidationService.getAllCommunityValidations().forEach(validation -> 
                communityValidationService.deleteCommunityValidation(validation.getValidationId()));
            
            expertFeedbackService.getAllExpertFeedbacks().forEach(feedback -> 
                expertFeedbackService.deleteExpertFeedback(feedback.getFeedbackId()));
            
            validationProjectService.getAllValidationProjects().forEach(project -> 
                validationProjectService.deleteValidationProject(project.getProjectId()));
            
            ideaService.getAllIdeas().forEach(idea -> 
                ideaService.deleteIdea(idea.getIdeaId()));
            
            userService.getAllUsers().forEach(user -> 
                userService.deleteUser(user.getUserId()));
            
            return ResponseEntity.ok("All data cleared successfully. Restart the application to recreate sample data.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error clearing data: " + e.getMessage());
        }
    }
    
    @GetMapping("/data-status")
    @Operation(summary = "Get data status", description = "Get the current status of data in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Data status retrieved successfully")
    })
    public ResponseEntity<String> getDataStatus() {
        try {
            long userCount = userService.getAllUsers().size();
            long ideaCount = ideaService.getAllIdeas().size();
            long projectCount = validationProjectService.getAllValidationProjects().size();
            long feedbackCount = expertFeedbackService.getAllExpertFeedbacks().size();
            long validationCount = communityValidationService.getAllCommunityValidations().size();
            long modelCount = financialModelService.getAllFinancialModels().size();
            long analyticsCount = analyticsService.getAllAnalytics().size();
            
            String status = String.format(
                "Database Status:\n" +
                "- Users: %d\n" +
                "- Ideas: %d\n" +
                "- Validation Projects: %d\n" +
                "- Expert Feedbacks: %d\n" +
                "- Community Validations: %d\n" +
                "- Financial Models: %d\n" +
                "- Analytics: %d",
                userCount, ideaCount, projectCount, feedbackCount, validationCount, modelCount, analyticsCount
            );
            
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting data status: " + e.getMessage());
        }
    }
}

