package com.cml.springbootskills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.cml.springbootskills.day3.repositories")
//@EnableJpaRepositories("com.cml.springbootskills.day3.repositories")
//@ComponentScan(basePackages = { "com.cml.springbootskills." })
public class SpringbootSkillsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSkillsApplication.class, args);
	}

}
