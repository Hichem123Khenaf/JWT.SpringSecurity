package com.training.spring.security.jwtspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.training.spring.security.jwtspringsecurity"})
@EntityScan(basePackages = {"com.training.spring.security.jwtspringsecurity"})
@EnableJpaAuditing
@EnableScheduling
public class JwtSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecurityApplication.class, args);
    }

}
