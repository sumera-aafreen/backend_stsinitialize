package com.example.sumera.sumera.controller;

import com.example.sumera.sumera.entity.User;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for user management and authentication")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user details by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "User ID") @PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Get user details by username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByUsername(
            @Parameter(description = "Username") @PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Get user details by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email") @PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<List<User>> getAllUsers(
            @Parameter(description = "Role") @RequestParam(required = false) User.UserRole role,
            @Parameter(description = "Expertise") @RequestParam(required = false) String expertise,
            @Parameter(description = "Active only") @RequestParam(required = false) Boolean activeOnly) {
        
        List<User> users;
        if (role != null) {
            users = userService.getUsersByRole(role);
        } else if (expertise != null) {
            users = userService.getUsersByExpertise(expertise);
        } else if (activeOnly != null && activeOnly) {
            users = userService.getActiveUsers();
        } else {
            users = userService.getAllUsers();
        }
        
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID") @PathVariable Long id,
            @RequestBody User user) {
        user.setUserId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/verify")
    @Operation(summary = "Verify user", description = "Verify a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User verified successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> verifyUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        User verifiedUser = userService.verifyUser(id);
        return verifiedUser != null ? ResponseEntity.ok(verifiedUser) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/login")
    @Operation(summary = "Update last login", description = "Update user's last login timestamp")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateLastLogin(
            @Parameter(description = "User ID") @PathVariable Long id) {
        User user = userService.updateLastLogin(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/check-username/{username}")
    @Operation(summary = "Check username availability", description = "Check if username is available")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Username availability checked")
    })
    public ResponseEntity<Boolean> checkUsernameAvailability(
            @Parameter(description = "Username") @PathVariable String username) {
        boolean available = !userService.existsByUsername(username);
        return ResponseEntity.ok(available);
    }
    
    @GetMapping("/check-email/{email}")
    @Operation(summary = "Check email availability", description = "Check if email is available")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email availability checked")
    })
    public ResponseEntity<Boolean> checkEmailAvailability(
            @Parameter(description = "Email") @PathVariable String email) {
        boolean available = !userService.existsByEmail(email);
        return ResponseEntity.ok(available);
    }
}
