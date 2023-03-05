package com.example.cheerdo;

import com.example.cheerdo.entity.Role;
import com.example.cheerdo.entity.enums.RoleName;
import com.example.cheerdo.login.service.LoginService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class CheerdoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheerdoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(LoginService LoginService) {
        return args -> {
            LoginService.saveRole(new Role(null, RoleName.ROLE_USER));
            LoginService.saveRole(new Role(null, RoleName.ROLE_ADMIN));
        };
    }

}
