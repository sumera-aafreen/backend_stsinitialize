package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.ExpertFeedback;
import com.example.sumera.sumera.service.ExpertFeedbackService;
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

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback Management", description = "APIs for managing feedback across the platform")
public class FeedbackController {
    
    private final ExpertFeedbackService expertFeedbackService;
    
    @PostMapping("/expert")
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
    
    @GetMapping("/project/{id}")
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
}

