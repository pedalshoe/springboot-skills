/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.day3.controllers;

import com.cml.springbootskills.day3.dto.CreateUserRequest;
import com.cml.springbootskills.day3.dto.UpdateUserRequest;
import com.cml.springbootskills.day3.dto.UserResponse;
import com.cml.springbootskills.day3.entities.User;
import com.cml.springbootskills.day3.exceptions.DuplicateEmailException;
import com.cml.springbootskills.day3.exceptions.UserNotFoundException;
import com.cml.springbootskills.day3.mappers.UserMapper;
import com.cml.springbootskills.day3.repositories.UserRepository;
import jakarta.validation.Valid;
import java.util.List;
//import com.cml.springbootskills.day3.repositories.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * since i have a User table and i created it using Vscode before. i'll try to
 * recreate the REST controller for day3 quickly
 *
 * @author christopher
 */
@RestController
@RequestMapping("/day3/users")
public class UserController {

    private final UserRepository repo;
    private final UserMapper mapper;

    public UserController(UserRepository repo, UserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * GET /day3/users Get all users
     */
    @GetMapping
    public List<UserResponse> getAllUsers() {
        System.out.println("Got HERE: getAllUsers()...");
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * get user by ID
     * @param id
     * @return 
     */
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = repo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapper.toResponse(user);
    }

    /**
     * search METHOD.GET day3/users/search?email=xxx
     */
    @GetMapping("/search")
    public UserResponse searchByEmail(@RequestParam String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        
        return mapper.toResponse(user);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        System.out.println("Got HERE: createUser()...");
        if( repo.existsByEmail(request.email)) {
            throw new DuplicateEmailException(request.email);
        }
        
        User user = mapper.toEntity(request);
        User savedUser = repo.save(user);
  
        return mapper.toResponse(savedUser);
    }
    
    /**
     * PUT /day3/users/{id}
     * Update an existing user with validation
     */
    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        // Check if email is being changed and if it already exists
        if (request.email != null && !request.email.equals(user.getEmail())) {
            if (repo.existsByEmail(request.email)) {
                throw new DuplicateEmailException(request.email);
            }
        }
        
        // Update entity from DTO (only non-null fields)
        mapper.updateEntity(request, user);
        
        // Save and return response
        User updatedUser = repo.save(user);
        return mapper.toResponse(updatedUser);
    }
    
    /**
     * PATCH /day3/users/{id}
     * Partially update a user
     */
    @PatchMapping("/{id}")
    public UserResponse patchUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        
        User user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        // Check if email is being changed and if it already exists
        if (request.email != null && !request.email.equals(user.getEmail())) {
            if (repo.existsByEmail(request.email)) {
                throw new DuplicateEmailException(request.email);
            }
        }
        
        // Update entity from DTO (only non-null fields)
        mapper.updateEntity(request, user);
        
        // Save and return response
        User updatedUser = repo.save(user);
        return mapper.toResponse(updatedUser);
    }
    
    /**
     * Delete /day3/users/{id}
     * 
     * @return 
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        if(!repo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repo.deleteById(id);
    }
    
    @GetMapping("/ping")
    public String ping() {
        return "UserController is working!";
    }
    
    @PostMapping("/test-post")
public String testPost() {
    return "POST is working!";
}

   
}
