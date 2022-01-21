package com.example.springsecuritywebfluxjwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringSecurityWebfluxJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityWebfluxJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(@Value("${config.message:NotFound}") String message) {
		return args -> log.info(">>> Message: {}", message);
	}

}
