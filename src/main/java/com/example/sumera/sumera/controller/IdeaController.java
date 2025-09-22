package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.service.IdeaService;
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
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
@Tag(name = "Idea Management", description = "APIs for managing startup ideas")
public class IdeaController {
    
    private final IdeaService ideaService;
    
    @PostMapping
    @Operation(summary = "Submit new idea", description = "Submit a new startup idea with confidentiality settings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Idea created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Idea> submitIdea(@RequestBody Idea idea) {
        try {
            Idea createdIdea = ideaService.createIdea(idea);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIdea);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get idea details", description = "Retrieve idea details with access validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Idea retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Idea not found")
    })
    public ResponseEntity<Idea> getIdea(
            @Parameter(description = "Idea ID") @PathVariable Long id) {
        Optional<Idea> idea = ideaService.getIdeaById(id);
        return idea.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update idea", description = "Update idea with version control")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Idea updated successfully"),
        @ApiResponse(responseCode = "404", description = "Idea not found")
    })
    public ResponseEntity<Idea> updateIdea(
            @Parameter(description = "Idea ID") @PathVariable Long id,
            @RequestBody Idea idea) {
        idea.setIdeaId(id);
        Idea updatedIdea = ideaService.updateIdea(idea);
        return ResponseEntity.ok(updatedIdea);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Archive idea", description = "Archive idea with audit trail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Idea archived successfully"),
        @ApiResponse(responseCode = "404", description = "Idea not found")
    })
    public ResponseEntity<Void> deleteIdea(
            @Parameter(description = "Idea ID") @PathVariable Long id) {
        ideaService.deleteIdea(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/share")
    @Operation(summary = "Share idea", description = "Share idea with confidentiality controls")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Idea shared successfully"),
        @ApiResponse(responseCode = "404", description = "Idea not found")
    })
    public ResponseEntity<Idea> shareIdea(
            @Parameter(description = "Idea ID") @PathVariable Long id,
            @RequestParam Idea.ConfidentialityLevel confidentialityLevel) {
        Idea sharedIdea = ideaService.shareIdea(id, confidentialityLevel);
        return sharedIdea != null ? ResponseEntity.ok(sharedIdea) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/pottfolio")
    @Operation(summary = "Get user's idea portfolio", description = "Get all ideas owned by a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Portfolio retrieved successfully")
    })
    public ResponseEntity<List<Idea>> getIdeaPortfolio(
            @Parameter(description = "User ID") @RequestParam Long userId) {
        List<Idea> ideas = ideaService.getIdeasByOwner(userId);
        return ResponseEntity.ok(ideas);
    }
    
    @GetMapping
    @Operation(summary = "Get all ideas", description = "Get all ideas with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ideas retrieved successfully")
    })
    public ResponseEntity<List<Idea>> getAllIdeas(
            @Parameter(description = "Confidentiality level") @RequestParam(required = false) Idea.ConfidentialityLevel confidentialityLevel,
            @Parameter(description = "Stage") @RequestParam(required = false) String stage,
            @Parameter(description = "Target market") @RequestParam(required = false) String targetMarket) {
        
        List<Idea> ideas;
        if (confidentialityLevel != null) {
            ideas = ideaService.getIdeasByConfidentialityLevel(confidentialityLevel);
        } else if (stage != null) {
            ideas = ideaService.getIdeasByStage(stage);
        } else if (targetMarket != null) {
            ideas = ideaService.getIdeasByTargetMarket(targetMarket);
        } else {
            ideas = ideaService.getAllIdeas();
        }
        
        return ResponseEntity.ok(ideas);
    }
    
    @GetMapping("/public")
    @Operation(summary = "Get public ideas", description = "Get all publicly accessible ideas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Public ideas retrieved successfully")
    })
    public ResponseEntity<List<Idea>> getPublicIdeas() {
        List<Idea> ideas = ideaService.getPublicIdeas();
        return ResponseEntity.ok(ideas);
    }
    
    @GetMapping("/investor-accessible")
    @Operation(summary = "Get investor accessible ideas", description = "Get ideas accessible to investors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Investor accessible ideas retrieved successfully")
    })
    public ResponseEntity<List<Idea>> getInvestorAccessibleIdeas() {
        List<Idea> ideas = ideaService.getIdeasForInvestors();
        return ResponseEntity.ok(ideas);
    }
}
