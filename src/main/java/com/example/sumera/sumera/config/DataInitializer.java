package com.example.sumera.sumera.config;

import com.example.sumera.sumera.entity.Idea;
import com.example.sumera.sumera.entity.User;
import com.example.sumera.sumera.entity.ValidationProject;
import com.example.sumera.sumera.service.IdeaService;
import com.example.sumera.sumera.service.UserService;
import com.example.sumera.sumera.service.ValidationProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// @Component  // Disabled - No automatic data initialization
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    private final IdeaService ideaService;
    private final ValidationProjectService validationProjectService;
    
    @Override
    public void run(String... args) throws Exception {
        // Only create sample data if it doesn't exist
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("Creating sample data...");
            createSampleUsers();
            createSampleIdeas();
            createSampleValidationProjects();
            System.out.println("Sample data created successfully!");
        } else {
            System.out.println("Sample data already exists. Skipping data initialization.");
        }
    }
    
    private void createSampleUsers() {
        try {
            // Create Entrepreneur
            if (!userService.existsByUsername("john_entrepreneur")) {
                User entrepreneur = new User();
                entrepreneur.setUsername("john_entrepreneur");
                entrepreneur.setEmail("john@example.com");
                entrepreneur.setPasswordHash("hashed_password_123");
                entrepreneur.setRole(User.UserRole.ENTREPRENEUR);
                entrepreneur.setExpertise("Technology");
                entrepreneur.setVerificationStatus(true);
                entrepreneur.setProfileData("Experienced entrepreneur with 5+ years in tech startups");
                entrepreneur.setIsActive(true);
                userService.createUser(entrepreneur);
                System.out.println("Created entrepreneur user: john_entrepreneur");
            }
            
            // Create Expert
            if (!userService.existsByUsername("sarah_expert")) {
                User expert = new User();
                expert.setUsername("sarah_expert");
                expert.setEmail("sarah@example.com");
                expert.setPasswordHash("hashed_password_456");
                expert.setRole(User.UserRole.EXPERT);
                expert.setExpertise("Market Research, Business Strategy");
                expert.setVerificationStatus(true);
                expert.setProfileData("Senior market research analyst with 10+ years experience");
                expert.setIsActive(true);
                userService.createUser(expert);
                System.out.println("Created expert user: sarah_expert");
            }
            
            // Create Investor
            if (!userService.existsByUsername("mike_investor")) {
                User investor = new User();
                investor.setUsername("mike_investor");
                investor.setEmail("mike@example.com");
                investor.setPasswordHash("hashed_password_789");
                investor.setRole(User.UserRole.INVESTOR);
                investor.setExpertise("Venture Capital, Fintech");
                investor.setVerificationStatus(true);
                investor.setProfileData("Venture capitalist specializing in fintech and B2B SaaS");
                investor.setIsActive(true);
                userService.createUser(investor);
                System.out.println("Created investor user: mike_investor");
            }
        } catch (Exception e) {
            System.out.println("Error creating users: " + e.getMessage());
        }
    }
    
    private void createSampleIdeas() {
        // Get the entrepreneur user
        User entrepreneur = userService.getUserByUsername("john_entrepreneur").orElse(null);
        if (entrepreneur == null) return;
        
        // Create sample idea 1
        Idea idea1 = new Idea();
        idea1.setOwner(entrepreneur);
        idea1.setTitle("AI-Powered Personal Finance Assistant");
        idea1.setDescription("An intelligent personal finance app that uses AI to provide personalized financial advice and budgeting recommendations.");
        idea1.setProblemStatement("Many people struggle with personal finance management and lack access to affordable financial advice.");
        idea1.setSolution("AI-powered app that analyzes spending patterns and provides personalized recommendations.");
        idea1.setTargetMarket("Young professionals aged 25-40");
        idea1.setBusinessModel("Freemium with premium subscription");
        idea1.setStage("Early Stage");
        idea1.setConfidentialityLevel(Idea.ConfidentialityLevel.PUBLIC);
        ideaService.createIdea(idea1);
        
        // Create sample idea 2
        Idea idea2 = new Idea();
        idea2.setOwner(entrepreneur);
        idea2.setTitle("Sustainable Packaging Solutions");
        idea2.setDescription("Eco-friendly packaging materials made from agricultural waste for e-commerce companies.");
        idea2.setProblemStatement("E-commerce companies need sustainable packaging solutions to reduce environmental impact.");
        idea2.setSolution("Biodegradable packaging materials made from agricultural waste.");
        idea2.setTargetMarket("E-commerce companies and online retailers");
        idea2.setBusinessModel("B2B sales with volume-based pricing");
        idea2.setStage("Concept Stage");
        idea2.setConfidentialityLevel(Idea.ConfidentialityLevel.PRIVATE);
        ideaService.createIdea(idea2);
    }
    
    private void createSampleValidationProjects() {
        // Get the entrepreneur and ideas
        User entrepreneur = userService.getUserByUsername("john_entrepreneur").orElse(null);
        if (entrepreneur == null) return;
        
        // Get the first idea
        Idea idea = ideaService.getIdeasByOwner(entrepreneur.getUserId()).stream()
                .findFirst().orElse(null);
        if (idea == null) return;
        
        // Create validation project
        ValidationProject project = new ValidationProject();
        project.setIdea(idea);
        project.setValidationPlan("Comprehensive market validation including customer interviews, competitive analysis, and financial modeling");
        project.setMilestones("1. Market Research, 2. Customer Validation, 3. Financial Analysis, 4. Expert Review");
        project.setStatus(ValidationProject.ProjectStatus.IN_PROGRESS);
        project.setBudget(new BigDecimal("50000"));
        project.setProgressMetrics("25% complete - Market research phase");
        validationProjectService.createValidationProject(project);
    }
}
