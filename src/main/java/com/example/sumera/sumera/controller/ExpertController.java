package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.ExpertFeedback;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.service.ExpertFeedbackService;
import com.example.sumera.sumera.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/experts")
@RequiredArgsConstructor
@Tag(name = "Expert Network", description = "APIs for expert network and feedback management")
public class ExpertController {
    
    private final UserService userService;
    private final ExpertFeedbackService expertFeedbackService;
    
    @GetMapping("/search")
    @Operation(summary = "Search for experts", description = "Search for experts by criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Experts found successfully")
    })
    public ResponseEntity<List<User>> searchExperts(
            @Parameter(description = "Expertise area") @RequestParam(required = false) String expertise,
            @Parameter(description = "Role") @RequestParam(required = false) User.UserRole role) {
        
        List<User> experts;
        if (expertise != null) {
            experts = userService.getUsersByExpertise(expertise);
        } else if (role != null) {
            experts = userService.getUsersByRole(role);
        } else {
            experts = userService.getUsersByRole(User.UserRole.EXPERT);
        }
        
        return ResponseEntity.ok(experts);
    }
    
    @PostMapping("/consultation")
    @Operation(summary = "Book expert consultation", description = "Book a consultation with an expert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consultation booked successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> bookExpertConsultation(@RequestBody String consultationData) {
        // This would be implemented with a proper consultation service
        return ResponseEntity.status(HttpStatus.CREATED).body("Consultation booked successfully");
    }
    
    @GetMapping("/{id}/availability")
    @Operation(summary = "Get expert availability", description = "Get availability schedule for an expert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Expert not found")
    })
    public ResponseEntity<String> getExpertAvailability(
            @Parameter(description = "Expert ID") @PathVariable Long id) {
        Optional<User> expert = userService.getUserById(id);
        if (expert.isPresent()) {
            return ResponseEntity.ok("Expert availability data");
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/feedback/expert")
    @Operation(summary = "Submit expert feedback", description = "Submit feedback from an expert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Feedback submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ExpertFeedback> submitExpertFeedback(@RequestBody ExpertFeedback expertFeedback) {
        try {
            ExpertFeedback createdFeedback = expertFeedbackService.createExpertFeedback(expertFeedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/feedback/project/{id}")
    @Operation(summary = "Get project feedback summary", description = "Get summary of expert feedback for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedback summary retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<List<ExpertFeedback>> getProjectFeedbackSummary(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        List<ExpertFeedback> feedbacks = expertFeedbackService.getExpertFeedbacksByProject(id);
        return ResponseEntity.ok(feedbacks);
    }
    
    @GetMapping("/feedback")
    @Operation(summary = "Get expert feedbacks", description = "Get expert feedbacks with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feedbacks retrieved successfully")
    })
    public ResponseEntity<List<ExpertFeedback>> getExpertFeedbacks(
            @Parameter(description = "Expert ID") @RequestParam(required = false) Long expertId,
            @Parameter(description = "Project ID") @RequestParam(required = false) Long projectId,
            @Parameter(description = "Feedback type") @RequestParam(required = false) String feedbackType) {
        
        List<ExpertFeedback> feedbacks;
        if (expertId != null) {
            feedbacks = expertFeedbackService.getExpertFeedbacksByExpert(expertId);
        } else if (projectId != null) {
            feedbacks = expertFeedbackService.getExpertFeedbacksByProject(projectId);
        } else if (feedbackType != null) {
            feedbacks = expertFeedbackService.getExpertFeedbacksByType(feedbackType);
        } else {
            feedbacks = expertFeedbackService.getAllExpertFeedbacks();
        }
        
        return ResponseEntity.ok(feedbacks);
    }
    
    @PostMapping("/feedback/submit")
    @Operation(summary = "Submit feedback with details", description = "Submit detailed expert feedback")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Feedback submitted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ExpertFeedback> submitDetailedFeedback(
            @RequestParam Long projectId,
            @RequestParam Long expertId,
            @RequestParam String feedbackType,
            @RequestParam Integer rating,
            @RequestParam String comments,
            @RequestParam String recommendations) {
        
        ExpertFeedback feedback = expertFeedbackService.submitFeedback(
            projectId, expertId, feedbackType, rating, comments, recommendations);
        
        return feedback != null ? 
            ResponseEntity.status(HttpStatus.CREATED).body(feedback) : 
            ResponseEntity.badRequest().build();
    }
}
