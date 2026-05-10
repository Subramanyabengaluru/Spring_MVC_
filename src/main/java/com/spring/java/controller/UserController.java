package com.spring.java.controller;

import com.spring.java.dto.ApiResponse;
import com.spring.java.dto.UserRequest;
import com.spring.java.dto.UserResponse;
import com.spring.java.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest userRequest,
            HttpServletRequest request) {
        log.info("🌐 [UserController] POST /users - Creating new user");
        log.debug("📦 [UserController] Request body: {}", userRequest);

        try {
            UserResponse userResponse = userService.createUser(userRequest);
            ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("User created successfully")
                    .data(userResponse)
                    .timestamp(LocalDateTime.now())
                    .path(request.getRequestURI())
                    .build();

            log.info("✅ [UserController] User created with id: {}", userResponse.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("❌ [UserController] Error creating user: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(HttpServletRequest request) {
        log.info("🌐 [UserController] GET /users/getAllUsers - Fetching all users");

        try {
            List<UserResponse> users = userService.getAllUsers();
            log.info("✅ [UserController] Retrieved {} users from service", users.size());

            ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                    .status(HttpStatus.OK.value())
                    .message("Users retrieved successfully")
                    .data(users)
                    .timestamp(LocalDateTime.now())
                    .path(request.getRequestURI())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("❌ [UserController] Error fetching all users: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id,
            HttpServletRequest request) {
        log.info("🌐 [UserController] GET /users/{} - Fetching user by id", id);

        try {
            UserResponse userResponse = userService.getUserById(id);
            log.info("✅ [UserController] User found with id: {}", id);

            ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message("User retrieved successfully")
                    .data(userResponse)
                    .timestamp(LocalDateTime.now())
                    .path(request.getRequestURI())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("❌ [UserController] Error fetching user by id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(
            @PathVariable Long id,
            HttpServletRequest request) {
        log.info("🌐 [UserController] DELETE /users/{} - Deleting user", id);

        try {
            String message = userService.deleteUserById(id);
            log.info("✅ [UserController] User deleted with id: {}", id);

            ApiResponse<String> response = ApiResponse.<String>builder()
                    .status(HttpStatus.OK.value())
                    .message("User deleted successfully")
                    .data(message)
                    .timestamp(LocalDateTime.now())
                    .path(request.getRequestURI())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("❌ [UserController] Error deleting user with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
