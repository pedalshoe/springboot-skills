/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.day3.exceptions;

/**
 *
 * @author christopher
 */
public class DuplicateUserException extends RuntimeException {
        
        public DuplicateUserException() {
            super();
        }
        
        public DuplicateUserException(String email) {
            super("User with email " + email + " already exists!");
        }    
}
