/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.day3.controllers;

import com.cml.springbootskills.day3.dto.CreateUserRequest;
import com.cml.springbootskills.day3.dto.UserResponse;
import com.cml.springbootskills.day3.entities.User;
import com.cml.springbootskills.day3.exceptions.UserNotFoundException;
import com.cml.springbootskills.day3.mappers.UserMapper;
import com.cml.springbootskills.day3.repositories.UserRepository;
import java.util.List;
//import com.cml.springbootskills.day3.repositories.UserRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody CreateUserRequest body) {
        User u = new User();
        u.setEmail(body.email);
        u.setDisplayName(body.displayName);
        return repo.save(u);
    }

   
}
