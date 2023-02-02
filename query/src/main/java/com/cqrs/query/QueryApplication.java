package com.cqrs.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cqrs.core")
public class QueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class, args);
    }
}
