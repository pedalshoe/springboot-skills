/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.day3.exceptions;

/**
 *
 * @author christopher
 */
public class UserNotFoundException extends RuntimeException {
        
        public UserNotFoundException() {
            super();
        }
        
        public UserNotFoundException(Long id) {
            super("User " + id + " not found");
        }

        public UserNotFoundException(String email) {
            super("User with email " + email + " not found");
        }    
}
