package com.spring.java.service;

import com.spring.java.dto.UserRequest;
import com.spring.java.dto.UserResponse;
import com.spring.java.entity.User;
import com.spring.java.exception.ResourceNotFoundException;
import com.spring.java.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;

    public UserResponse createUser(UserRequest userRequest) {
        log.info("📝 [UserService] Creating user: name={}, email={}", userRequest.getName(), userRequest.getEmail());
        try {
            User user = mapper.map(userRequest, User.class);
            log.debug("🔄 [UserService] Mapped UserRequest to User entity: {}", user);

            User savedUser = userRepo.save(user);
            log.info("✅ [UserService] User saved successfully: id={}, name={}", savedUser.getId(), savedUser.getName());

            UserResponse response = mapper.map(savedUser, UserResponse.class);
            log.debug("🔄 [UserService] Mapped User to UserResponse: {}", response);

            return response;
        } catch (Exception e) {
            log.error("❌ [UserService] Error creating user: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Cacheable(value = "users", key = "'all-users'")
    public List<UserResponse> getAllUsers() {
        log.info("📋 [UserService] Fetching all users from database");
        try {
            List<User> users = userRepo.findAll();
            log.info("✅ [UserService] Retrieved {} users from database", users.size());

            List<UserResponse> responses = users.stream()
                    .map(user -> mapper.map(user, UserResponse.class))
                    .toList();

            log.debug("🔄 [UserService] Mapped {} User entities to UserResponse", responses.size());
            return responses;
        } catch (Exception e) {
            log.error("❌ [UserService] Error fetching all users: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching users: " + e.getMessage(), e);
        }
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserById(Long id) {
        log.info("🔍 [UserService] Fetching user by id: {}", id);
        try {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> {
                        log.warn("⚠️  [UserService] User not found with id: {}", id);
                        return new ResourceNotFoundException("User not found with id: " + id);
                    });

            log.info("✅ [UserService] User found: id={}, name={}", user.getId(), user.getName());

            UserResponse response = mapper.map(user, UserResponse.class);
            log.debug("🔄 [UserService] Mapped User to UserResponse: {}", response);

            return response;
        } catch (ResourceNotFoundException e) {
            log.error("❌ [UserService] Resource not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ [UserService] Error fetching user by id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching user: " + e.getMessage(), e);
        }
    }

    public String deleteUserById(Long id) {
        log.info("🗑️  [UserService] Deleting user with id: {}", id);
        try {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> {
                        log.warn("⚠️  [UserService] User not found for deletion with id: {}", id);
                        return new ResourceNotFoundException("User not found with id: " + id);
                    });

            userRepo.delete(user);
            log.info("✅ [UserService] User deleted successfully: id={}, name={}", id, user.getName());

            return "User deleted successfully with id: " + id;
        } catch (ResourceNotFoundException e) {
            log.error("❌ [UserService] Resource not found for deletion: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ [UserService] Error deleting user with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }
}
