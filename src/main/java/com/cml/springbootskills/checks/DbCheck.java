/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cml.springbootskills.checks;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author christopher
 */
public class DbCheck {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void check() {
        jdbcTemplate.execute("SELECT 1");
        System.out.println("✅ Database connected successfully!");
    }
    
}
