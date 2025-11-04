package com.cml.springbootskills;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@EnableJpaRepositories(basePackages = "com.cml.springbootskills.day3.repositories")
class SpringbootSkillsApplicationTests {

	@Test
	void contextLoads() {
	}

}
