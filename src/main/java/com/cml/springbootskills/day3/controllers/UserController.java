/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.day3.controllers;

import com.cml.springbootskills.day3.dto.CreateUserRequest;
import com.cml.springbootskills.day3.entities.User;
import com.cml.springbootskills.day3.repositories.UserRepository;
//import com.cml.springbootskills.day3.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * search METHOD.GET day3/users/
     */
    @GetMapping("/search")
    public User byEmail(@RequestParam String email) {
        return repo.findByEmail(email).orElseThrow(() -> new UserNotFound(email));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody CreateUserRequest body) {
        User u = new User();
        u.setEmail(body.email);
        u.setDisplayName(body.displayName);
        return repo.save(u);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class UserNotFound extends RuntimeException {

        UserNotFound(Long id) {
            super("User " + id + " not found");
        }

        UserNotFound(String email) {
            super("User with email " + email + " not found");
        }
    }

}