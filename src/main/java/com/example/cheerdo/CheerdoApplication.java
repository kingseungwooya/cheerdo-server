package com.example.cheerdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CheerdoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheerdoApplication.class, args);
    }

}
