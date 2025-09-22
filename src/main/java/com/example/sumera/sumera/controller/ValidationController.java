package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.service.ValidationProjectService;
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
@RequestMapping("/api/validation")
@RequiredArgsConstructor
@Tag(name = "Validation Process", description = "APIs for managing validation projects and processes")
public class ValidationController {
    
    private final ValidationProjectService validationProjectService;
    
    @PostMapping("/projects")
    @Operation(summary = "Create validation project", description = "Create a new validation project for an idea")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Validation project created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ValidationProject> createValidationProject(@RequestBody ValidationProject validationProject) {
        try {
            ValidationProject createdProject = validationProjectService.createValidationProject(validationProject);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/projects/{id}")
    @Operation(summary = "Get validation project details", description = "Get detailed information about a validation project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> getValidationProject(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        Optional<ValidationProject> project = validationProjectService.getValidationProjectById(id);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/projects/{id}/progress")
    @Operation(summary = "Update validation progress", description = "Update the progress of a validation project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progress updated successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> updateValidationProgress(
            @Parameter(description = "Project ID") @PathVariable Long id,
            @RequestParam String progressMetrics) {
        ValidationProject updatedProject = validationProjectService.updateProjectProgress(id, progressMetrics);
        return updatedProject != null ? ResponseEntity.ok(updatedProject) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/projects/{id}/insights")
    @Operation(summary = "Get validation insights", description = "Get insights and analytics for a validation project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insights retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> getValidationInsights(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        Optional<ValidationProject> project = validationProjectService.getValidationProjectById(id);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/surveys")
    @Operation(summary = "Create validation survey", description = "Create a new validation survey")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Survey created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createValidationSurvey(@RequestBody String surveyData) {
        // This would be implemented with a proper survey service
        return ResponseEntity.status(HttpStatus.CREATED).body("Survey created successfully");
    }
    
    @GetMapping("/sutveys/{id}/responses")
    @Operation(summary = "Get survey responses", description = "Get responses for a validation survey")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Survey responses retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Survey not found")
    })
    public ResponseEntity<String> getSurveyResponses(
            @Parameter(description = "Survey ID") @PathVariable Long id) {
        // This would be implemented with a proper survey service
        return ResponseEntity.ok("Survey responses");
    }
    
    @GetMapping("/projects")
    @Operation(summary = "Get validation projects", description = "Get validation projects with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
    })
    public ResponseEntity<List<ValidationProject>> getValidationProjects(
            @Parameter(description = "User ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "Status") @RequestParam(required = false) ValidationProject.ProjectStatus status,
            @Parameter(description = "Idea ID") @RequestParam(required = false) Long ideaId) {
        
        List<ValidationProject> projects;
        if (userId != null && status != null) {
            projects = validationProjectService.getValidationProjectsByOwnerAndStatus(userId, status);
        } else if (userId != null) {
            projects = validationProjectService.getValidationProjectsByOwner(userId);
        } else if (status != null) {
            projects = validationProjectService.getValidationProjectsByStatus(status);
        } else if (ideaId != null) {
            Optional<ValidationProject> project = validationProjectService.getValidationProjectByIdea(ideaId);
            projects = project.map(List::of).orElse(List.of());
        } else {
            projects = validationProjectService.getAllValidationProjects();
        }
        
        return ResponseEntity.ok(projects);
    }
    
    @PutMapping("/projects/{id}/status")
    @Operation(summary = "Update project status", description = "Update the status of a validation project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> updateProjectStatus(
            @Parameter(description = "Project ID") @PathVariable Long id,
            @RequestParam ValidationProject.ProjectStatus status) {
        ValidationProject updatedProject = validationProjectService.updateProjectStatus(id, status);
        return updatedProject != null ? ResponseEntity.ok(updatedProject) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/projects/{id}/start")
    @Operation(summary = "Start validation", description = "Start the validation process for a project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validation started successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> startValidation(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        ValidationProject project = validationProjectService.startValidation(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/projects/{id}/complete")
    @Operation(summary = "Complete validation", description = "Mark a validation project as completed")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Validation completed successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ValidationProject> completeValidation(
            @Parameter(description = "Project ID") @PathVariable Long id) {
        ValidationProject project = validationProjectService.completeValidation(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }
}
